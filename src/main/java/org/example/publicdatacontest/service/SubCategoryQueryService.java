package org.example.publicdatacontest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.publicdatacontest.domain.category.SubCategory;
import org.example.publicdatacontest.repository.category.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
@RequiredArgsConstructor
public class SubCategoryQueryService {

    private final SubCategoryRepository subCategoryRepository;

    // 받은 소분류가 실제 db에 있는지 확인
    // 존재하면 그 리스트를 Set으로 반환
    public Set<SubCategory> subCategories(Set<Long> subCategoryId) {
        return new HashSet<>(
                subCategoryRepository.findBySubCategoryIdIn(
                        new HashSet<>(subCategoryId)
                )
        );
    }

}