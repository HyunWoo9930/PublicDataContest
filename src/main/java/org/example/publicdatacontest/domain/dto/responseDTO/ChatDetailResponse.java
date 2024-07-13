package org.example.publicdatacontest.domain.dto.responseDTO;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatDetailResponse {
	private List<ChatResponse> chatResponse;
	private List<PaymentStatusHistoryResponse> paymentStatus;

	public ChatDetailResponse(List<ChatResponse> chatResponse, List<PaymentStatusHistoryResponse> paymentStatus) {
		this.chatResponse = chatResponse;
		this.paymentStatus = paymentStatus;
	}
}
