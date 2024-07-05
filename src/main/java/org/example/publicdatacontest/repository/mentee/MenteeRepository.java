package org.example.publicdatacontest.repository.mentee;

import java.util.Optional;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeRepository extends JpaRepository<Mentee, Long> {
	Optional<Mentee> findByUserId(String id);

	boolean existsByUserId(String id);
}
