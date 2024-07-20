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
import org.example.publicdatacontest.domain.util.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("MENTOR")
public class Mentor extends User {
	private Long mentoringCount;
	private Long studentCount;
	private Boolean reemploymentIdea;

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
