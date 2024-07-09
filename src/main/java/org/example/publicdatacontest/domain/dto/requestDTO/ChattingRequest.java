package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChattingRequest {
	private Long conversationId;
	private Long senderId;
	private Long receiverId;
	private String content;

	public ChattingRequest(Long conversationId, Long senderId, Long receiverId, String content) {
		this.conversationId = conversationId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.content = content;
	}
}
