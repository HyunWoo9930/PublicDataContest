package org.example.publicdatacontest.repository.category;

import org.example.publicdatacontest.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
