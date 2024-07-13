package org.example.publicdatacontest.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import org.example.publicdatacontest.domain.PaymentStatus;

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

	public PaymentStatusHistory() {
	}

	public PaymentStatusHistory(Conversation conversation, PaymentStatus paymentStatus, LocalDateTime timestamp) {
		this.conversation = conversation;
		this.paymentStatus = paymentStatus;
		this.timestamp = timestamp;
	}
}
