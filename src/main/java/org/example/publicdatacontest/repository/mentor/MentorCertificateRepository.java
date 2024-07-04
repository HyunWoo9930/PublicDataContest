package org.example.publicdatacontest.repository.mentor;

import org.example.publicdatacontest.domain.mentor.MentorCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorCertificateRepository extends JpaRepository<MentorCertificate, Long> {
}
