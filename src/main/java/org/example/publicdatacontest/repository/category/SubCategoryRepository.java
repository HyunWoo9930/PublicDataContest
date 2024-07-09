package org.example.publicdatacontest.repository.category;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findBySubCategoryIdIn(Set<Long> subCategoryId);
}
