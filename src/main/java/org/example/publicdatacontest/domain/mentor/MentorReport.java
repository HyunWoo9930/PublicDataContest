package org.example.publicdatacontest.domain.mentor;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.Mentee;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MentorReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;

	@ManyToOne
	@JoinColumn(name = "mentor_id", nullable = false)
	private Mentor mentor;

	@ManyToOne
	@JoinColumn(name = "mentee_id", nullable = false)
	private Mentee mentee;

	private String reason;
	private LocalDateTime reportDate;

	public MentorReport() {}

	public MentorReport(Mentor mentor, Mentee mentee, String reason, LocalDateTime reportDate) {
		this.mentor = mentor;
		this.mentee = mentee;
		this.reason = reason;
		this.reportDate = reportDate;
	}
}
