package org.example.publicdatacontest.repository.mentor;

import java.util.Optional;

import org.example.publicdatacontest.domain.mentor.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
	Optional<Mentor> findByUserId(String userid);

	boolean existsByUserId(String userid);

	boolean existsByEmail(String email);

	Optional<Mentor> findByEmail(String email);
}
