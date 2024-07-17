package org.example.publicdatacontest.repository.mentor;

import java.util.Optional;

import org.example.publicdatacontest.domain.mentor.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
	Optional<Mentor> findByUserId(String id);

	boolean existsByUserId(String id);

	boolean existsByEmail(String email);
}
