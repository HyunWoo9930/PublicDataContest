package org.example.publicdatacontest.repository.mentee;

import org.example.publicdatacontest.domain.mentee.MenteeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeCategoryRepository extends JpaRepository<MenteeCategory, Long> {
}
