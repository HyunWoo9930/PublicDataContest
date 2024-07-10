package org.example.publicdatacontest.repository.mentor;

import org.example.publicdatacontest.domain.dto.responseDTO.MentorClassResponse;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentorClassRepository extends JpaRepository<MentorClass, Long> {
}
