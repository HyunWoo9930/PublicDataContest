package org.example.publicdatacontest.controller;

import org.example.publicdatacontest.domain.dto.responseDTO.CertificateResponse;
import org.example.publicdatacontest.domain.mentor.Certificate;
import org.example.publicdatacontest.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/certificates")
@CrossOrigin(origins = "*")
public class CertificateController {
	@Autowired
	private CertificateService certificateService;

	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadCertificate(
		@AuthenticationPrincipal UserDetails userDetails,
		@Parameter(name = "file", description = "업로드 사진 데이터")
		@RequestParam(value = "file") MultipartFile file) {
		try {
			certificateService.saveCertificate(userDetails, file);
			return ResponseEntity.ok("Certificate uploaded successfully");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (IOException e) {
			return ResponseEntity.status(500).body("Error uploading certificate: " + e.getMessage());
		}
	}

	@GetMapping("/get_certificate")
	public ResponseEntity<?> getCertificate(
		@RequestParam(value = "mentor_id") Long id
	) {
		try {
			List<CertificateResponse> certificates = certificateService.getCertificate(id);
			return ResponseEntity.ok(certificates);
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
