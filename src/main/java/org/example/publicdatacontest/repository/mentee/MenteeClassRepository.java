package org.example.publicdatacontest.repository.mentee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeClassRepository
	extends JpaRepository<org.example.publicdatacontest.domain.mentee.MenteeClass, Long> {
}
