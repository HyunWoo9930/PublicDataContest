package org.example.publicdatacontest.domain.chat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Conversation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long conversationId;

	@ManyToOne
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	@ManyToOne
	@JoinColumn(name = "mentee_id")
	private Mentee mentee;

	private LocalDateTime startDate;

	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<PaymentStatusHistory> paymentStatusHistories;

	@OneToMany(mappedBy = "conversation")
	private Set<Chat> chats;

	public Conversation(Long conversationId, Mentor mentor, Mentee mentee, LocalDateTime startDate, Set<Chat> chats) {
		this.conversationId = conversationId;
		this.mentor = mentor;
		this.mentee = mentee;
		this.startDate = startDate;
		this.chats = chats;
	}

	public Conversation(Mentor mentor, Mentee mentee, LocalDateTime startDate) {
		this.mentor = mentor;
		this.mentee = mentee;
		this.startDate = startDate;
	}

	public Conversation() {
	}

	public void addPaymentStatusHistory(PaymentStatusHistory paymentStatusHistory) {
		this.paymentStatusHistories.add(paymentStatusHistory);
		paymentStatusHistory.setConversation(this);
	}

	public void removePaymentStatusHistory(PaymentStatusHistory paymentStatusHistory) {
		this.paymentStatusHistories.remove(paymentStatusHistory);
		paymentStatusHistory.setConversation(null);
	}
}
