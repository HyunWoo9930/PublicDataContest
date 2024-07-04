package org.example.publicdatacontest.repository.mentor;

import org.example.publicdatacontest.domain.mentor.MentorCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorCategoryRepository extends JpaRepository<MentorCategory, Long> {
}
