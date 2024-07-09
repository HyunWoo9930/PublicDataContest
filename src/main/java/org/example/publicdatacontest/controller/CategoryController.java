package org.example.publicdatacontest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.publicdatacontest.domain.category.SubCategorySaveRequest;
import org.example.publicdatacontest.service.MentorCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CategoryController {

    private final MentorCategoryService mentorCategoryService;

    @PostMapping("subCategory")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody SubCategorySaveRequest saveRequest,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(mentorCategoryService.saveMentorSubCategory(saveRequest, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
