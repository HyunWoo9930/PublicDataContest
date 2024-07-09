package org.example.publicdatacontest.controller;

import java.util.List;

import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.mentor.MentorClassRequest;
import org.example.publicdatacontest.domain.mentor.MentorClassResponse;
import org.example.publicdatacontest.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mentoring")
@CrossOrigin(origins = "*")
public class ClassController {

	@Autowired
	ClassService classService;

	@PostMapping("/make_mentoring")
	public ResponseEntity<?> makeMentoring(
		@AuthenticationPrincipal UserDetails userDetails,
		@Valid @RequestBody MentorClassRequest mentorClassRequest
	) {
		try {
			classService.makeMentoring(userDetails, mentorClassRequest);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/mentoring_list")
	public ResponseEntity<?> mentoringList(
	) {
		List<MentorClassResponse> mentorClasses = classService.mentoringList();
		return ResponseEntity.ok(mentorClasses);
	}
}
