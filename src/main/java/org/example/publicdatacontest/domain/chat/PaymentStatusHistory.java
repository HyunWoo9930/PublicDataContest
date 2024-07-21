package org.example.publicdatacontest.domain.chat;

import java.time.LocalDateTime;

import org.example.publicdatacontest.domain.util.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaymentStatusHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "conversation_id")
	private Conversation conversation;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private LocalDateTime timestamp;
	private String sender;
	private Long requestedClassId;
	private Boolean reviewCheck;

	public PaymentStatusHistory() {
	}

	public PaymentStatusHistory(Conversation conversation, PaymentStatus paymentStatus, LocalDateTime timestamp,
		String sender, Long requestedClassId, Boolean reviewCheck) {
		this.conversation = conversation;
		this.paymentStatus = paymentStatus;
		this.timestamp = timestamp;
		this.sender = sender;
		this.requestedClassId = requestedClassId;
		this.reviewCheck = reviewCheck;
	}
}
