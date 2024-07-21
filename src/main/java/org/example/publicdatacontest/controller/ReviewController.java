package org.example.publicdatacontest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.example.publicdatacontest.domain.dto.requestDTO.ReviewRequest;
import org.example.publicdatacontest.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity<?> saveReview(@Valid @RequestBody ReviewRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {

        try {
            return ResponseEntity.ok(reviewService.writeReview(request, userDetails));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> reviewDetail(@RequestParam(value = "reviewId") Long reviewId) {
        try {
            return ResponseEntity.ok(reviewService.reviewDetail(reviewId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/mentee")
    public ResponseEntity<?> getReviewListOfMentee(@AuthenticationPrincipal UserDetails userDetails) {

        try {
            return ResponseEntity.ok(reviewService.getReviewListOfMentee(userDetails));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/class")
    public ResponseEntity<?> getReviewListOfClass(@RequestParam(value = "classId") Long classId) {

        try {
            return ResponseEntity.ok(reviewService.getReviewListOfClass(classId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
