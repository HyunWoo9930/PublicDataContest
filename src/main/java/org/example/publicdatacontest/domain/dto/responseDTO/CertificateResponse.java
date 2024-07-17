package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;
import java.util.Base64;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificateResponse {
	private Long certificateId;
	private String image;
	private LocalDateTime uploadDate;
	private Long mentorId;

	public CertificateResponse(Long certificateId, byte[] image, LocalDateTime uploadDate, Long mentorId) {
		this.certificateId = certificateId;
		this.image = Base64.getEncoder().encodeToString(image);
		this.uploadDate = uploadDate;
		this.mentorId = mentorId;
	}
}
