package org.example.publicdatacontest.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.domain.category.SubCategorySaveRequest;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorCategory;
import org.example.publicdatacontest.repository.mentor.MentorCategoryRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MentorCategoryService {

    private final MentorCategoryRepository mentorCategoryRepository;
    private final MentorRepository mentorRepository;

    private final SubCategoryQueryService subCategoryQueryService;


    public String saveMentorSubCategory(SubCategorySaveRequest saveRequest, UserDetails userDetails) {

        Optional<Mentor> exists = mentorRepository.findByUserId(userDetails.getUsername());
        if (exists.isEmpty()) {
            throw new RuntimeException("멘토가 아니거나, 유저가 없습니다.");
        }

        Mentor mentor = exists.get();

        // 받은 소분류 리스트가 유효한 리스트인지 확인
        log.info("id List: " + saveRequest.getSubCategoryId());

        var set = new HashSet<>(saveRequest.getSubCategoryId());
        var existingSubCategorySet = subCategoryQueryService.subCategories(set);

        if (existingSubCategorySet.isEmpty()) {
            throw new RuntimeException("소분류가 선택되지 않았습니다.");
        }

        for (SubCategory sc : existingSubCategorySet) {
            mentorCategoryRepository.save(MentorCategory.of(mentor.getMentorId(), sc.getSubCategoryId(), mentor, sc, sc.getName(), sc.getCategory().getName()));
        }

        return "MentorCategory saved successfully";
    }


}
