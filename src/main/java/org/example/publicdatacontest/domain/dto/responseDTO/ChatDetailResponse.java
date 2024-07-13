package org.example.publicdatacontest.domain.dto.responseDTO;

import java.util.List;
import java.util.Set;

import org.example.publicdatacontest.domain.PaymentStatus;
import org.example.publicdatacontest.domain.chat.PaymentStatusHistory;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatDetailResponse {
	private List<ChatResponse> chatResponse;
	private Set<PaymentStatusHistory> paymentStatus;

	public ChatDetailResponse(List<ChatResponse> chatResponse, Set<PaymentStatusHistory> paymentStatus) {
		this.chatResponse = chatResponse;
		this.paymentStatus = paymentStatus;
	}
}
