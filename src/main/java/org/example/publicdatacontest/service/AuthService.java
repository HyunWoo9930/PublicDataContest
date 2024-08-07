package org.example.publicdatacontest.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.publicdatacontest.domain.dto.requestDTO.ReportUserRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.MenteeInfoResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MenteeResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorInfoResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorReport;
import org.example.publicdatacontest.domain.signinup.LoginRequest;
import org.example.publicdatacontest.domain.signinup.SignUpRequest;
import org.example.publicdatacontest.jwt.JwtAuthenticationResponse;
import org.example.publicdatacontest.jwt.JwtTokenProvider;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorReportRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MentorRepository mentorRepository;

	@Autowired
	MenteeRepository menteeRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	private final MentorReportRepository mentorReportRepository;

	private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
		"image/png",
		"image/jpeg",
		"image/jpg"
	);

	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

	public AuthService(MentorReportRepository mentorReportRepository) {
		this.mentorReportRepository = mentorReportRepository;
	}

	public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(),
				loginRequest.getPassword()
			)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication.getName());
		Optional<Mentee> byUserId = menteeRepository.findByUserId(loginRequest.getUsername());
		if (byUserId.isPresent()) {
			return new JwtAuthenticationResponse(jwt, "mentee");
		} else {
			Optional<Mentor> mentor = mentorRepository.findByUserId(loginRequest.getUsername());
			if (mentor.isPresent()) {
				return new JwtAuthenticationResponse(jwt, "mentor");
			} else {
				throw new NotFoundException("User Not Found");
			}
		}
	}

	@Transactional
	public String registerMentor(SignUpRequest signUpRequest) {
		if (mentorRepository.existsByUserId(signUpRequest.getUserId()) | menteeRepository.existsByUserId(
			signUpRequest.getUserId())) {
			throw new RuntimeException("Username is already taken!");
		}

		Mentor mentor = new Mentor();
		mentor.setUserId(signUpRequest.getUserId());
		mentor.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		mentor.setName(signUpRequest.getName());
		mentor.setAddress(signUpRequest.getAddress());
		mentor.setEmail(signUpRequest.getEmail());
		mentor.setBirth(signUpRequest.getBirth());
		mentor.setGender(signUpRequest.getGender());
		mentor.setIsEmailAlarmAgreed(signUpRequest.getIsEmailAlarmAgreed());
		mentor.setReemploymentIdea(signUpRequest.getEmploymentIdea());
		mentor.setPhoneNumber(signUpRequest.getPhoneNumber());

		mentorRepository.save(mentor);

		Optional<Mentor> mentor1 = mentorRepository.findByUserId(signUpRequest.getUserId());
		if (mentor1.isPresent()) {
			return "User registered successfully";
		} else {
			throw new RuntimeException("User registered Failed");
		}
	}

	@Transactional
	public String registerMentee(SignUpRequest signUpRequest) {
		if (menteeRepository.existsByUserId(signUpRequest.getUserId()) | mentorRepository.existsByUserId(
			signUpRequest.getUserId())) {
			throw new RuntimeException("Username is already taken!");
		}

		Mentee mentee = new Mentee();
		mentee.setUserId(signUpRequest.getUserId());
		mentee.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		mentee.setName(signUpRequest.getName());
		mentee.setAddress(signUpRequest.getAddress());
		mentee.setEmail(signUpRequest.getEmail());
		mentee.setBirth(signUpRequest.getBirth());
		mentee.setGender(signUpRequest.getGender());
		mentee.setIsEmailAlarmAgreed(signUpRequest.getIsEmailAlarmAgreed());
		mentee.setEmploymentIdea(signUpRequest.getEmploymentIdea());
		mentee.setPhoneNumber(signUpRequest.getPhoneNumber());

		menteeRepository.save(mentee);

		Optional<Mentee> byUserId = menteeRepository.findByUserId(signUpRequest.getUserId());
		if (byUserId.isPresent()) {
			return "User registered successfully";
		} else {
			throw new RuntimeException("User registered Failed");
		}
	}

	public Object getInfo(UserDetails userDetails) {
		String username = userDetails.getUsername();
		Optional<Mentor> mentor = mentorRepository.findByUserId(username);
		if (mentor.isPresent()) {
			return new MentorInfoResponse(convertToDTO(mentor.get()), "mentor");
		} else {
			Optional<Mentee> mentee = menteeRepository.findByUserId(username);
			if (mentee.isPresent()) {
				return new MenteeInfoResponse(convertToDTO(mentee.get()), "mentee");
			} else {
				throw new NotFoundException("user not found");
			}
		}
	}

	public Object getMyInfo(Long user_id) {
		Optional<Mentor> mentor = mentorRepository.findById(user_id);
		if (mentor.isPresent()) {
			return new MentorInfoResponse(convertToDTO(mentor.get()), "mentor");
		} else {
			Optional<Mentee> mentee = menteeRepository.findById(user_id);
			if (mentee.isPresent()) {
				return new MenteeInfoResponse(convertToDTO(mentee.get()), "mentee");
			} else {
				throw new NotFoundException("user not found");
			}
		}
	}

	public List<MentorResponse> getAllMentorInfo() {
		return mentorRepository.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	public List<MenteeResponse> getAllMenteeInfo() {
		return menteeRepository.findAll().stream()
			.map(this::convertToDTO)
			.collect(Collectors.toList());
	}

	public MentorResponse getMentorById(Long id) {
		Mentor mentor = mentorRepository.findById(id).orElseThrow(() -> new NotFoundException("Mentor not found"));
		return convertToDTO(mentor);
	}

	private MentorResponse convertToDTO(Mentor mentor) {
		return new MentorResponse(mentor);
	}

	public MenteeResponse getMenteeById(Long id) {
		Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new NotFoundException("Mentee not found"));
		return convertToDTO(mentee);
	}

	private MenteeResponse convertToDTO(Mentee mentee) {
		return new MenteeResponse(mentee);
	}

	public void registerPay(UserDetails userDetails, String payment) {
		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> {
			mentor.setPaymentMethod(payment);
			mentorRepository.save(mentor);
		});
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> {
			mentee.setPaymentMethod(payment);
			menteeRepository.save(mentee);
		});
	}

	public void reportUser(UserDetails userDetails, ReportUserRequest reportUserRequest) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("User is not active");
		}
		Mentor mentor = mentorRepository.findById(reportUserRequest.getReportedUserId())
			.orElseThrow(() -> new NotFoundException("Mentor not found"));
		Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new NotFoundException("Mentee not found"));

		mentorReportRepository.save(
			new MentorReport(mentor, mentee, reportUserRequest.getReportContent(), LocalDateTime.now()));
	}

	public Boolean idDuplicateCheck(String userId) {
		return mentorRepository.existsByUserId(userId) | menteeRepository.existsByUserId(userId);
	}

	public Boolean emailDuplicateCheck(String email) {
		return mentorRepository.existsByEmail(email) | menteeRepository.existsByEmail(email);
	}

	public String findId(String email) {
		Optional<Mentor> mentor = mentorRepository.findByEmail(email);
		if (mentor.isPresent()) {
			return mentor.get().getUserId();
		} else {
			Optional<Mentee> mentee = menteeRepository.findByEmail(email);
			if (mentee.isPresent()) {
				return mentee.get().getUserId();
			} else {
				throw new NotFoundException("User not found");
			}
		}
	}

	public void changePassword(UserDetails userDetails, String password) {
		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> {
			mentor.setPassword(passwordEncoder.encode(password));
			mentorRepository.save(mentor);
		});
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> {
			mentee.setPassword(passwordEncoder.encode(password));
			menteeRepository.save(mentee);
		});
	}

	public void uploadProfileImage(UserDetails userDetails, MultipartFile profileImage) {
		if (!ALLOWED_CONTENT_TYPES.contains(profileImage.getContentType())) {
			throw new IllegalArgumentException("Only PNG, JPEG, and JPG files are allowed");
		}

		if (profileImage.getSize() > MAX_FILE_SIZE) {
			throw new IllegalArgumentException("File size must be less than 5MB");
		}

		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> {
			try {
				mentor.setProfilePicture(profileImage.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			mentorRepository.save(mentor);
		});
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> {
			try {
				mentee.setProfilePicture(profileImage.getBytes());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			menteeRepository.save(mentee);
		});
	}

	public byte[] getProfileImage(UserDetails userDetails) {
		Optional<Mentor> mentor = mentorRepository.findByUserId(userDetails.getUsername());
		if (mentor.isPresent() && mentor.get().getProfilePicture() != null) {
			return mentor.get().getProfilePicture();
		}

		Optional<Mentee> mentee = menteeRepository.findByUserId(userDetails.getUsername());
		if (mentee.isPresent() && mentee.get().getProfilePicture() != null) {
			return mentee.get().getProfilePicture();
		}

		throw new IllegalArgumentException("Profile picture not found for user: " + userDetails.getUsername());
	}

	public void deleteProfileImage(UserDetails userDetails) {
		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> {
			mentor.setProfilePicture(null);
			mentorRepository.save(mentor);
		});
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> {
			mentee.setProfilePicture(null);
			menteeRepository.save(mentee);
		});
	}

	@Transactional
	public void deleteUser(UserDetails userDetails) {
		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> mentorRepository.delete(mentor));
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> menteeRepository.delete(mentee));
	}

	public void toggleActive(UserDetails userDetails) {
		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> {
			mentor.setActive(!mentor.getActive());
			mentorRepository.save(mentor);
		});
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> {
			mentee.setActive(!mentee.getActive());
			menteeRepository.save(mentee);
		});
	}

	public void toggleEmployedIdea(UserDetails userDetails) {
		mentorRepository.findByUserId(userDetails.getUsername()).ifPresent(mentor -> {
			mentor.setReemploymentIdea(!mentor.getReemploymentIdea());
			mentorRepository.save(mentor);
		});
		menteeRepository.findByUserId(userDetails.getUsername()).ifPresent(mentee -> {
			mentee.setEmploymentIdea(!mentee.getEmploymentIdea());
			menteeRepository.save(mentee);
		});

	}


	public byte[] getProfileImageId(Long id) {
		Optional<Mentor> mentor = mentorRepository.findById(id);
		if (mentor.isPresent() && mentor.get().getProfilePicture() != null) {
			return mentor.get().getProfilePicture();
		}

		Optional<Mentee> mentee = menteeRepository.findById(id);
		if (mentee.isPresent() && mentee.get().getProfilePicture() != null) {
			return mentee.get().getProfilePicture();
		}

		throw new IllegalArgumentException("Profile picture not found for user: " + id);
	}
}
