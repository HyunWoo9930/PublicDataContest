package org.example.publicdatacontest.domain.util;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reports {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reportId;

	@ManyToOne
	@JoinColumn(name = "mentor_id")
	private Mentor mentor;

	@ManyToOne
	@JoinColumn(name = "mentee_id")
	private Mentee mentee;

	private String reason;
	private String details;
	private LocalDateTime timestamp;
}
