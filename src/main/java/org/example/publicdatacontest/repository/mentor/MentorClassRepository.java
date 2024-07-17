package org.example.publicdatacontest.repository.mentor;

import java.util.List;

import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorClassRepository extends JpaRepository<MentorClass, Long> {
	List<MentorClass> findAllByMentor_MentorId(Long mentorId);
}
