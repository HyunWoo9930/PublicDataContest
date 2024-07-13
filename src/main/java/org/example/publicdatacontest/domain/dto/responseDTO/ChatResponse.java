package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.chat.Chat;

@Setter
@Getter
public class ChatResponse {
	private Long messageId;
	private Long conversationId;
	private Long senderId;
	private String senderType;
	private String senderName;
	private Long receiverId;
	private String receiverType;
	private String receiverName;
	private String content;
	private LocalDateTime timestamp;

	public ChatResponse(Chat chat) {
		this.messageId = chat.getMessageId();
		this.conversationId = chat.getConversation().getConversationId();
		this.senderId = chat.getSenderId();
		this.senderType = chat.getSenderType();
		this.senderName = chat.getSenderName();
		this.receiverId = chat.getReceiverId();
		this.receiverType = chat.getReceiverType();
		this.receiverName = chat.getReceiverName();
		this.content = chat.getContent();
		this.timestamp = chat.getTimestamp();
	}
}
