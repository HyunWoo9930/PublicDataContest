package org.example.publicdatacontest.domain.mentor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.util.Reports;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@Table
@EntityListeners(AuditingEntityListener.class)
public class Mentor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mentorId;
	private String userId;
	private String password;
	private String mentorName;
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
	private Long mentoringCount;
	private Long studentCount;
	private Boolean reemploymentIdea;
	private Boolean active;
	private Boolean isEmailAlarmAgreed;
	private String paymentMethod;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "mentor")
	private Set<MentorCertificate> certificates;

	@OneToMany(mappedBy = "mentor")
	private Set<MentorBadge> badges;

	@OneToMany(mappedBy = "mentor")
	private Set<MentorClass> mentorClasses;

	@OneToMany(mappedBy = "mentor")
	private Set<Reports> reports;

	@OneToMany(mappedBy = "mentor")
	private Set<Conversation> conversations;

	@OneToMany(mappedBy = "mentor")
	private Set<MentorCategory> mentorCategories;

	public void addMentorCategory(MentorCategory mentorCategory) {
		this.mentorCategories.add(mentorCategory);
		mentorCategory.setMentor(this);
	}
}
