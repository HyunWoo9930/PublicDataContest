package org.example.publicdatacontest.domain.mentee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.util.Reports;
import org.example.publicdatacontest.domain.util.Review;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
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
}

