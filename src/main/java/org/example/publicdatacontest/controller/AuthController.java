package org.example.publicdatacontest.controller;

import java.util.List;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.signinup.LoginRequest;
import org.example.publicdatacontest.domain.signinup.SignUpRequest;
import org.example.publicdatacontest.jwt.JwtAuthenticationResponse;
import org.example.publicdatacontest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public JwtAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.authenticateUser(loginRequest);

	}

	@PostMapping("/signup/mentor")
	public ResponseEntity<?> registerMentor(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			String registered = authService.registerMentor(signUpRequest);
			return ResponseEntity.ok(registered);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/signup/mentee")
	public ResponseEntity<?> registerMentee(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			String registered = authService.registerMentee(signUpRequest);
			return ResponseEntity.ok(registered);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/getInfo")
	public ResponseEntity<?> getInfo(@AuthenticationPrincipal UserDetails userDetails) {
		try {
			Object info = authService.getInfo(userDetails);
			System.out.println("info = " + info);
			return ResponseEntity.ok(info);
		} catch (NotFoundException | UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/getAllMentorInfo")
	public ResponseEntity<?> getAllMentorInfo(@AuthenticationPrincipal UserDetails userDetails) {
		List<Mentor> allMentorInfo = authService.getAllMentorInfo(userDetails);
		return ResponseEntity.ok(allMentorInfo);
	}

	@GetMapping("/getAllMenteeInfo")
	public ResponseEntity<?> getAlMenteeInfo(@AuthenticationPrincipal UserDetails userDetails) {
		List<Mentee> allMenteeInfo = authService.getAllMenteeInfo(userDetails);
		return ResponseEntity.ok(allMenteeInfo);
	}
}
