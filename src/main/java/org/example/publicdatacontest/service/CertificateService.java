package org.example.publicdatacontest.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.example.publicdatacontest.domain.dto.responseDTO.CertificateResponse;
import org.example.publicdatacontest.domain.mentor.Certificate;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.repository.mentor.CertificateRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

@Service
public class CertificateService {
	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private MentorRepository mentorRepository;
	private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
		"image/png",
		"image/jpeg",
		"image/jpg"
	);

	private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

	public Certificate saveCertificate(UserDetails userDetails, MultipartFile file) throws IOException {
		if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
			throw new IllegalArgumentException("Only PNG, JPEG, and JPG files are allowed");
		}

		if (file.getSize() > MAX_FILE_SIZE) {
			throw new IllegalArgumentException("File size must be less than 5MB");
		}

		Mentor mentor = mentorRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new IllegalArgumentException("Mentor not found"));

		Certificate certificate = new Certificate();
		certificate.setMentor(mentor);
		certificate.setImage(file.getBytes());
		certificate.setImageName(file.getOriginalFilename());

		return certificateRepository.save(certificate);
	}

	public List<CertificateResponse> getCertificate(Long id) {
		Mentor mentor = mentorRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Mentor not found"));

		return certificateRepository.findAllByMentorId(mentor.getId())
			.stream()
			.map(certificate -> new CertificateResponse(
				certificate.getId(),
				certificate.getImage(),
				certificate.getUploadTime(),
				certificate.getMentor().getId()
			))
			.toList();
	}
}
