package org.example.publicdatacontest.domain.util;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.MentorClass;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

	private Review(MentorClass mentorClass, Mentee mentee, Long rating, String comment) {
		if(mentorClass == null) throw new InvalidParameterException();
		if(mentee == null) throw new InvalidParameterException();

		this.rating = rating;
		this.comment = comment;
		mentorClass.addReview(this);
		mentee.addReview(this);
		this.timestamp = LocalDateTime.now();
	}

	public static Review of(MentorClass mentorClass, Mentee mentee, Long rating, String comment) {
		return new Review(mentorClass, mentee, rating, comment);
	}
}
