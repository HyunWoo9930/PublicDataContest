package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatResponse {
	private Long messageId;
	private Long conversationId;
	private Long senderId;
	private String senderType;
	private Long receiverId;
	private String receiverType;
	private String content;
	private LocalDateTime timestamp;

	public ChatResponse(Long messageId, Long conversationId, Long senderId, String senderType, Long receiverId,
		String receiverType, String content, LocalDateTime timestamp) {
		this.messageId = messageId;
		this.conversationId = conversationId;
		this.senderId = senderId;
		this.senderType = senderType;
		this.receiverId = receiverId;
		this.receiverType = receiverType;
		this.content = content;
		this.timestamp = timestamp;
	}
}
