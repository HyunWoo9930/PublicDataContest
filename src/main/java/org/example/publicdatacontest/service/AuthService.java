package org.example.publicdatacontest.service;

import java.util.List;
import java.util.Optional;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeInfoResponse;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorInfoResponse;
import org.example.publicdatacontest.domain.signinup.LoginRequest;
import org.example.publicdatacontest.domain.signinup.SignUpRequest;
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
		return new JwtAuthenticationResponse(jwt);
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

		return "User registered successfully";
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

		return "User registered successfully";
	}

	public Object getInfo(UserDetails userDetails) {
		String username = userDetails.getUsername();
		Optional<Mentor> mentor = mentorRepository.findByUserId(username);
		if (mentor.isPresent()) {
			return new MentorInfoResponse(mentor.get(), "mentor");
		} else {
			Optional<Mentee> mentee = menteeRepository.findByUserId(username);
			if (mentee.isPresent()) {
				return new MenteeInfoResponse(mentee.get(), "mentee");
			} else {
				throw new NotFoundException("user not found");
			}
		}
	}

	public List<Mentor> getAllMentorInfo(UserDetails userDetails) {
		return mentorRepository.findAll();
	}

	public List<Mentee> getAllMenteeInfo(UserDetails userDetails) {
		return menteeRepository.findAll();
	}
}
