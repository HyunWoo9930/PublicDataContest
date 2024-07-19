package org.example.publicdatacontest.domain.mentee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.util.Reports;
import org.example.publicdatacontest.domain.util.Review;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
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
public class Mentee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menteeId;
	private String userId;
	private String password;
	private String menteeName;
	@NotBlank
	@Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
	private String gender;

	@Past(message = "Birthdate must be in the past")
	private LocalDate birth;

	@NotBlank
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank
	@Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number should be between 10 and 15 digits")
	private String phoneNumber;
	private String address;
	private Boolean employmentIdea;
	private Boolean active;
	private Boolean isEmailAlarmAgreed;
	private String paymentMethod;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

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

	public Mentee(Long menteeId, String userId, String password, String menteeName, String gender, LocalDate birth,
		String email, String phoneNumber, String address, Boolean employmentIdea, Boolean active,
		Boolean isEmailAlarmAgreed, String paymentMethod, LocalDateTime createdAt, Set<Reports> reports,
		Set<Conversation> conversations, Set<MenteeClass> menteeClasses, Set<MenteeCategory> menteeCategories,
		Set<Review> reviews) {
		this.menteeId = menteeId;
		this.userId = userId;
		this.password = password;
		this.menteeName = menteeName;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.employmentIdea = employmentIdea;
		this.active = active;
		this.isEmailAlarmAgreed = isEmailAlarmAgreed;
		this.paymentMethod = paymentMethod;
		this.createdAt = createdAt;
		this.reports = reports;
		this.conversations = conversations;
		this.menteeClasses = menteeClasses;
		this.menteeCategories = menteeCategories;
		this.reviews = reviews;
	}
}

