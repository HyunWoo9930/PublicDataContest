package org.example.publicdatacontest.domain.paymentHistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentee.MenteeClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PaymentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "class_id", referencedColumnName = "class_id"),
		@JoinColumn(name = "mentee_id", referencedColumnName = "mentee_id")
	})
	private MenteeClass menteeClass;

	private LocalDateTime paymentDate;
	private Long amount;
	private Long price;

	public PaymentHistory(MenteeClass menteeClass, LocalDateTime paymentDate, Long amount, Long price) {
		this.menteeClass = menteeClass;
		this.paymentDate = paymentDate;
		this.amount = amount;
		this.price = price;
	}

	public PaymentHistory() {

	}
}
