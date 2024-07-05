package org.example.publicdatacontest.domain.mentee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.util.Reports;
import org.example.publicdatacontest.domain.util.Review;

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
	private String gender;
	private String birth;
	private String email;
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

