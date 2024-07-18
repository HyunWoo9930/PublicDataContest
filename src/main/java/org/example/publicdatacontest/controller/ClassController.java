package org.example.publicdatacontest.controller;

import java.util.List;

import org.example.publicdatacontest.domain.dto.requestDTO.MentorClassRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorClassResponse;
import org.example.publicdatacontest.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
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
	@Operation(summary = "전체 멘토링 리스트 조회")
	public ResponseEntity<?> mentoringList(
	) {
		List<MentorClassResponse> mentorClasses = classService.mentoringList();
		return ResponseEntity.ok(mentorClasses);
	}


	@GetMapping("/mentoring_detail")
	@Operation(summary = "멘토링 상세 조회")
	public ResponseEntity<?> mentoringDetail(
			@RequestParam(value = "classId") Long classId
	) {
		try {
			MentorClassResponse mentorClassResponse = classService.mentoringDetail(classId);
			return ResponseEntity.ok(mentorClassResponse);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/category_filtering")
	public ResponseEntity<?> mentoringListByCategory(@RequestParam(value = "categoryId") Long categoryId) {
		List<MentorClassResponse> mentorClasses = classService.mentoringListByCategory(categoryId);
		return ResponseEntity.ok(mentorClasses);
	}

	@GetMapping("/subCategory_filtering")
	public ResponseEntity<?> mentoringListBySubCategory(@RequestParam(value = "subCategoryId") Long subCategoryId) {
		List<MentorClassResponse> mentorClasses = classService.mentoringListBySubCategory(subCategoryId);
		return ResponseEntity.ok(mentorClasses);
	}

	@PutMapping("/update_mentoring")
	public ResponseEntity<?> updateMentoring(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam(value = "classId") Long classId
	) {
		try {
			classService.updateMentoring(userDetails, classId);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping("/final_finish_mentoring")
	public ResponseEntity<?> finalFinishMentoring(
		@RequestParam(value = "classId") Long classId
	) {
		try {
			classService.finalFinishMentoring(classId);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/get_mentor_mentoring")
	public ResponseEntity<?> getMentorMentoring(
		@AuthenticationPrincipal UserDetails userDetails
	) {
		List<MentorClassResponse> mentorClasses = classService.getMentorMentoring(userDetails);
		return ResponseEntity.ok(mentorClasses);
	}
}
