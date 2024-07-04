package org.example.publicdatacontest.domain.util;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.MentorClass;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewId;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private MentorClass mentorClass;

	@ManyToOne
	@JoinColumn(name = "mentee_id")
	private Mentee mentee;

	private Long rating;
	private String comment;
	private LocalDateTime timestamp;
}
