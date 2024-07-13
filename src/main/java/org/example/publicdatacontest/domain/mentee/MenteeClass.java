package org.example.publicdatacontest.domain.mentee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.paymentHistory.PaymentHistory;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@IdClass(MenteeClassId.class)
public class MenteeClass {
	@Id
	@Column(name = "class_id")
	private Long classId;

	@Id
	@Column(name = "mentee_id")
	private Long menteeId;

	@ManyToOne
	@JoinColumn(name = "class_id", insertable = false, updatable = false)
	private MentorClass mentorClass;

	@ManyToOne
	@JoinColumn(name = "mentee_id", insertable = false, updatable = false)
	private Mentee mentee;

	private Long count;
	private Long usedCount;
	private LocalDateTime timestamp;

	@OneToMany(mappedBy = "menteeClass")
	private Set<PaymentHistory> paymentHistories;
}
