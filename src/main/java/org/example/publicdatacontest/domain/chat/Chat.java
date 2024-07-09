package org.example.publicdatacontest.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long messageId;

	@ManyToOne
	@JoinColumn(name = "conversation_id")
	private Conversation conversation;

	private Long senderId;
	private String senderType;
	private Long receiverId;
	private String receiverType;
	private String content;
	private LocalDateTime timestamp;

	public Chat(Conversation conversation, Long senderId, String senderType, Long receiverId, String receiverType,
		String content, LocalDateTime timestamp) {
		this.conversation = conversation;
		this.senderId = senderId;
		this.senderType = senderType;
		this.receiverId = receiverId;
		this.receiverType = receiverType;
		this.content = content;
		this.timestamp = timestamp;
	}

	public Chat() {

	}
}
