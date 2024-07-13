package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import org.example.publicdatacontest.domain.util.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatusHistoryResponse {
	private Long id;
	private Long conversationId;
	private PaymentStatus paymentStatus;
	private LocalDateTime timestamp;
	private String sender;

	public PaymentStatusHistoryResponse(Long id, Long conversationId, PaymentStatus paymentStatus,
		LocalDateTime timestamp,
		String sender) {
		this.id = id;
		this.conversationId = conversationId;
		this.paymentStatus = paymentStatus;
		this.timestamp = timestamp;
		this.sender = sender;
	}
}
