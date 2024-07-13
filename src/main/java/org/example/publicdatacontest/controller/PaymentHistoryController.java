package org.example.publicdatacontest.controller;

import org.example.publicdatacontest.domain.dto.requestDTO.PaymentRequest;
import org.example.publicdatacontest.service.PaymentHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

@RestController
@RequestMapping("/api/payment_history")
@CrossOrigin(origins = "*")
public class PaymentHistoryController {

	private final PaymentHistoryService paymentHistoryService;

	public PaymentHistoryController(PaymentHistoryService paymentHistoryService) {
		this.paymentHistoryService = paymentHistoryService;
	}

	@PostMapping("/post_payment_history")
	public ResponseEntity<?> postPaymentHistory(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody PaymentRequest paymentRequest
	) {
		try {
			paymentHistoryService.postPaymentHistory(userDetails, paymentRequest);
			return ResponseEntity.ok("success");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/get_payment_history")
	public ResponseEntity<?> getPaymentHistory(
		@AuthenticationPrincipal UserDetails userDetails
	) {
		try {
			return ResponseEntity.ok(paymentHistoryService.getPaymentHistory(userDetails));
		} catch (NotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
