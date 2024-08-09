package org.example.publicdatacontest.repository.mentor;

import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MentorClassRepository extends JpaRepository<MentorClass, Long> {
    Optional<MentorClass> findByClassId(Long Id);

    List<MentorClass> findByNameContaining(String searchKeyword);
}
