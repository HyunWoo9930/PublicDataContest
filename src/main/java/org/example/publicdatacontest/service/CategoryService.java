package org.example.publicdatacontest.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.dto.requestDTO.SubCategorySaveRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.CategoryResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MenteeCategoryResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MentorCategoryResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeCategory;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorCategory;
import org.example.publicdatacontest.repository.category.CategoryRepository;
import org.example.publicdatacontest.repository.mentee.MenteeCategoryRepository;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorCategoryRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final MentorCategoryRepository mentorCategoryRepository;
    private final MentorRepository mentorRepository;
    private final MenteeCategoryRepository menteeCategoryRepository;
    private final MenteeRepository menteeRepository;

    private final CategoryRepository categoryRepository;

    private final SubCategoryService subCategoryService;


    public String saveMentorSubCategory(SubCategorySaveRequest saveRequest, UserDetails userDetails) {

        Optional<Mentor> exists = mentorRepository.findByUserId(userDetails.getUsername());
        if (exists.isEmpty()) {
            throw new RuntimeException("멘토가 아니거나, 유저가 없습니다.");
        }

        Mentor mentor = exists.get();

        // 받은 소분류 리스트가 유효한 리스트인지 확인
        log.info("id List: " + saveRequest.getSubCategoryId());

        var set = new HashSet<>(saveRequest.getSubCategoryId());
        var existingSubCategorySet = subCategoryService.subCategories(set);

        if (existingSubCategorySet.isEmpty()) {
            throw new RuntimeException("소분류가 선택되지 않았습니다.");
        }

        for (SubCategory sc : existingSubCategorySet) {
            mentorCategoryRepository.save(MentorCategory.of(mentor.getId(), sc.getSubCategoryId(), mentor, sc));
        }

        return "MentorCategory saved successfully";
    }


    public List<MentorCategoryResponse> getMentorCategory(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Optional<Mentor> mentor = mentorRepository.findByUserId(username);

        if (mentor.isPresent()) {
            return mentor.get().getMentorCategories().stream()
                    .map(MentorCategoryResponse::new)
                    .toList();
        } else {
            throw new NotFoundException("user not found");
        }
    }

    public String saveMenteeSubCategory(SubCategorySaveRequest saveRequest, UserDetails userDetails) {

        Optional<Mentee> exists = menteeRepository.findByUserId(userDetails.getUsername());
        if (exists.isEmpty()) {
            throw new RuntimeException("멘티가 아니거나, 유저가 없습니다.");
        }

        Mentee mentee = exists.get();

        log.info("id List: " + saveRequest.getSubCategoryId());

        var set = new HashSet<>(saveRequest.getSubCategoryId());
        var existingSubCategorySet = subCategoryService.subCategories(set);

        if (existingSubCategorySet.isEmpty()) {
            throw new RuntimeException("소분류가 선택되지 않았습니다.");
        }

        for (SubCategory sc : existingSubCategorySet) {
            menteeCategoryRepository.save(MenteeCategory.of(mentee.getId(), sc.getSubCategoryId(), mentee, sc));
        }

        return "MenteeCategory saved successfully";
    }


    public List<MenteeCategoryResponse> getMenteeCategory(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Optional<Mentee> mentee = menteeRepository.findByUserId(username);

        if (mentee.isPresent()) {
            return mentee.get().getMenteeCategories().stream()
                    .map(MenteeCategoryResponse::new)
                    .toList();
        } else {
            throw new NotFoundException("user not found");
        }
    }


    public List<CategoryResponse> categoryList() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::new)
                .toList();
    }

}

