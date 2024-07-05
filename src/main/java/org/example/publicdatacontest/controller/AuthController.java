package org.example.publicdatacontest.controller;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.jwt.JwtAuthenticationResponse;
import org.example.publicdatacontest.jwt.JwtTokenProvider;
import org.example.publicdatacontest.domain.signinup.LoginRequest;
import org.example.publicdatacontest.domain.signinup.SignUpRequest;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

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

	@PostMapping("/signin")
	public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

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

	@PostMapping("/signup/mentor")
	public String registerMentor(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (mentorRepository.existsByUserId(signUpRequest.getUserId())) {
			return "Username is already taken!";
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

	@PostMapping("/signup/mentee")
	public String registerMentee(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (menteeRepository.existsByUserId(signUpRequest.getUserId())) {
			return "Username is already taken!";
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

	@GetMapping("/getMentorInfo")
	public Mentor getMentorInfo(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		return mentorRepository.findByUserId(username).orElseThrow(() -> new RuntimeException("User not found"));
	}
}
