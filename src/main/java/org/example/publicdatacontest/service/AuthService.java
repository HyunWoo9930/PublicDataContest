package org.example.publicdatacontest.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.dto.responseDTO.MenteeInfoResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MenteeResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorInfoResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeCategory;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorBadge;
import org.example.publicdatacontest.domain.mentor.MentorCategory;
import org.example.publicdatacontest.domain.mentor.MentorCertificate;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.signinup.LoginRequest;
import org.example.publicdatacontest.domain.signinup.SignUpRequest;
import org.example.publicdatacontest.domain.util.Reports;
import org.example.publicdatacontest.domain.util.Review;
import org.example.publicdatacontest.jwt.JwtAuthenticationResponse;
import org.example.publicdatacontest.jwt.JwtTokenProvider;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
		mentor.setMentorName(signUpRequest.getName());
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
		mentee.setMenteeName(signUpRequest.getName());
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
		return new MentorResponse(
			mentor.getMentorId(),
			mentor.getUserId(),
			mentor.getMentorName(),
			mentor.getGender(),
			mentor.getBirth(),
			mentor.getEmail(),
			mentor.getPhoneNumber(),
			mentor.getAddress(),
			mentor.getMentoringCount(),
			mentor.getStudentCount(),
			mentor.getReemploymentIdea(),
			mentor.getActive(),
			mentor.getIsEmailAlarmAgreed(),
			mentor.getCreatedAt(),
			mentor.getCertificates().stream().map(MentorCertificate::getCertificateId).collect(Collectors.toSet()),
			mentor.getBadges().stream().map(MentorBadge::getBadgeId).collect(Collectors.toSet()),
			mentor.getMentorClasses().stream().map(MentorClass::getClassId).collect(Collectors.toSet()),
			mentor.getReports().stream().map(Reports::getReportId).collect(Collectors.toSet()),
			mentor.getConversations().stream().map(Conversation::getConversationId).collect(Collectors.toSet()),
			mentor.getMentorCategories().stream().map(MentorCategory::getSubCategoryId).collect(Collectors.toSet())
		);
	}

	public MenteeResponse getMenteeById(Long id) {
		Mentee mentee = menteeRepository.findById(id).orElseThrow(() -> new NotFoundException("Mentee not found"));
		return convertToDTO(mentee);
	}

	private MenteeResponse convertToDTO(Mentee mentee) {
		return new MenteeResponse(
			mentee.getMenteeId(),
			mentee.getUserId(),
			mentee.getMenteeName(),
			mentee.getGender(),
			mentee.getBirth(),
			mentee.getEmail(),
			mentee.getPhoneNumber(),
			mentee.getAddress(),
			mentee.getEmploymentIdea(),
			mentee.getActive(),
			mentee.getIsEmailAlarmAgreed(),
			mentee.getCreatedAt(),
			mentee.getReports().stream().map(Reports::getReportId).collect(Collectors.toSet()),
			mentee.getConversations().stream().map(Conversation::getConversationId).collect(Collectors.toSet()),
			mentee.getMenteeClasses().stream().map(MenteeClass::getClassId).collect(Collectors.toSet()),
			mentee.getMenteeCategories().stream().map(MenteeCategory::getSubCategoryId).collect(Collectors.toSet()),
			mentee.getReviews().stream().map(Review::getReviewId).collect(Collectors.toSet())
		);
	}
}
