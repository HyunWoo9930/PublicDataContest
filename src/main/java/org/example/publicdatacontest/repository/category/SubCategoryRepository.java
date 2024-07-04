package org.example.publicdatacontest.repository.category;

import org.example.publicdatacontest.domain.category.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
}
