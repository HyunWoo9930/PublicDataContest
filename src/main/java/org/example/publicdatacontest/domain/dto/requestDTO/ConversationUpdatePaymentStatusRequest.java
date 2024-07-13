package org.example.publicdatacontest.domain.dto.requestDTO;

import org.example.publicdatacontest.domain.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConversationUpdatePaymentStatusRequest {
	private Long conversationId;
	private PaymentStatus paymentStatus;

	public ConversationUpdatePaymentStatusRequest(Long conversationId, PaymentStatus paymentStatus) {
		this.conversationId = conversationId;
		this.paymentStatus = paymentStatus;
	}
}
