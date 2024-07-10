package org.example.publicdatacontest.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.publicdatacontest.domain.category.SubCategorySaveRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.CategoryResponse;
import org.example.publicdatacontest.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
=======
import org.springframework.web.bind.annotation.CrossOrigin;
>>>>>>> 1b858bd4f10f3661f03be148408596247883d8a3
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/subCategory/mentor")
    public ResponseEntity<?> saveMentorCategory(@Valid @RequestBody SubCategorySaveRequest saveRequest,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(categoryService.saveMentorSubCategory(saveRequest, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/subCategory/mentee")
    public ResponseEntity<?> saveMenteeCategory(@Valid @RequestBody SubCategorySaveRequest saveRequest,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(categoryService.saveMenteeSubCategory(saveRequest, userDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/subCategory/mentor")
    public ResponseEntity<?> findMentorCategory(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Object info = categoryService.getMentorCategory(userDetails);
            return ResponseEntity.ok(info);
        } catch (NotFoundException | UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/subCategory/mentee")
    public ResponseEntity<?> findMenteeCategory(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Object info = categoryService.getMenteeCategory(userDetails);
            return ResponseEntity.ok(info);
        } catch (NotFoundException | UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/categoryList")
    public ResponseEntity<?> getAllCategoryList() {
        List<CategoryResponse> allCategoryList = categoryService.categoryList();
        return ResponseEntity.ok(allCategoryList);
    }

    @GetMapping("/method")
    public void method() {
        categoryService.method();
    }
}
