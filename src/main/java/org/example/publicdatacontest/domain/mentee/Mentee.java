package org.example.publicdatacontest.domain.mentee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.util.Like;
import org.example.publicdatacontest.domain.util.Reports;
import org.example.publicdatacontest.domain.util.Review;
import org.example.publicdatacontest.domain.util.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("MENTEE")
public class Mentee extends User {
	private Boolean employmentIdea;

	@OneToMany(mappedBy = "mentee")
	private Set<Reports> reports;

	@OneToMany(mappedBy = "mentee")
	private Set<Conversation> conversations;

	@OneToMany(mappedBy = "mentee")
	private Set<MenteeClass> menteeClasses;

	@OneToMany(mappedBy = "mentee")
	private Set<MenteeCategory> menteeCategories;

	@OneToMany(mappedBy = "mentee")
	private Set<Review> reviews;

	@OneToMany(mappedBy = "mentee")
	private Set<Like> likes;

	public Mentee() {

	}

	public void addMenteeCategory(MenteeCategory menteeCategory) {
		this.menteeCategories.add(menteeCategory);
		menteeCategory.setMentee(this);
	}

	public void addReview(Review review) {
		this.reviews.add(review);
		review.setMentee(this);
	}

	public Mentee(Boolean employmentIdea, Set<Reports> reports, Set<Conversation> conversations,
		Set<MenteeClass> menteeClasses, Set<MenteeCategory> menteeCategories, Set<Review> reviews) {
		this.employmentIdea = employmentIdea;
		this.reports = reports;
		this.conversations = conversations;
		this.menteeClasses = menteeClasses;
		this.menteeCategories = menteeCategories;
		this.reviews = reviews;
	}
}

