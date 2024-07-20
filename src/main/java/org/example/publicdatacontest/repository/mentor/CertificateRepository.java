package org.example.publicdatacontest.repository.mentor;

import java.util.List;
import java.util.Set;

import org.example.publicdatacontest.domain.mentor.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
	List<Certificate> findAllByMentorId(Long id);
}
