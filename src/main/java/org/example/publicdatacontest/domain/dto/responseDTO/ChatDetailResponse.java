package org.example.publicdatacontest.domain.dto.responseDTO;

import java.util.List;

import org.example.publicdatacontest.domain.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatDetailResponse {
	public ChatDetailResponse(List<ChatResponse> chatResponse, PaymentStatus paymentStatus) {
		this.chatResponse = chatResponse;
		this.paymentStatus = paymentStatus;
	}

	private List<ChatResponse> chatResponse;
	private PaymentStatus paymentStatus;


}
