package org.example.publicdatacontest.controller;

import java.util.List;

import org.example.publicdatacontest.domain.dto.requestDTO.ReportUserRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.MenteeResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorResponse;
import org.example.publicdatacontest.domain.signinup.LoginRequest;
import org.example.publicdatacontest.domain.signinup.SignUpRequest;
import org.example.publicdatacontest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			return ResponseEntity.ok(authService.authenticateUser(loginRequest));
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
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
			return ResponseEntity.ok(info);
		} catch (NotFoundException | UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/getAllMentorInfo")
	public ResponseEntity<?> getAllMentorInfo() {
		List<MentorResponse> allMentorInfo = authService.getAllMentorInfo();
		return ResponseEntity.ok(allMentorInfo);
	}

	@GetMapping("/getAllMenteeInfo")
	public ResponseEntity<?> getAlMenteeInfo() {
		List<MenteeResponse> allMenteeInfo = authService.getAllMenteeInfo();
		return ResponseEntity.ok(allMenteeInfo);
	}

	@PutMapping("/register_pay")
	public ResponseEntity<?> registerPay(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam(value = "payment") String payment
	) {
		try {
			authService.registerPay(userDetails, payment);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/report_user")
	public ResponseEntity<?> reportUser(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody ReportUserRequest reportUserRequest
	) {
		try {
			authService.reportUser(userDetails, reportUserRequest);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/id_duplicate")
	public ResponseEntity<?> idDuplicate(
		@RequestParam(value = "userId") String userId) {
		if (!authService.idDuplicateCheck(userId)) {
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate id");
		}
	}

	@GetMapping("/email_duplicate")
	public ResponseEntity<?> emailDuplicate(
		@RequestParam(value = "email") String email) {
		if (!authService.emailDuplicateCheck(email)) {
			return ResponseEntity.ok("success");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("duplicate id");
		}
	}

	@GetMapping("/find_id")
	public ResponseEntity<?> findId(
		@RequestParam(value = "email") String email) {
		try {
			String userId = authService.findId(email);
			return ResponseEntity.ok(userId);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("change_password")
	public ResponseEntity<?> changePassword(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam(value = "new_password") String password
	) {
		try {
			authService.changePassword(userDetails, password);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(value = "/upload_profile_image", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadProfileImage(
		@AuthenticationPrincipal UserDetails userDetails,
		@Parameter(name = "file", description = "업로드 사진 데이터")
		@RequestParam(value = "file") MultipartFile file
	) {
		try {
			authService.uploadProfileImage(userDetails, file);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/get_profile_image")
	public ResponseEntity<?> getProfileImage(
		@AuthenticationPrincipal UserDetails userDetails
	) {
		try {
			byte[] image = authService.getProfileImage(userDetails);
			return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + userDetails.getUsername() + ".jpg\"")
				.body(image);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

}
