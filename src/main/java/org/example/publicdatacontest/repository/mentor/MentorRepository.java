package org.example.publicdatacontest.repository.mentor;


import org.example.publicdatacontest.domain.mentor.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<Mentor, Long> {
}
