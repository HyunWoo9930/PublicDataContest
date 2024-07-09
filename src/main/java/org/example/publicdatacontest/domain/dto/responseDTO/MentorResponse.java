package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class MentorResponse {
	private Long mentorId;
	private String userId;
	private String mentorName;
	private String gender;
	private LocalDate birth;
	private String email;
	private String phoneNumber;
	private String address;
	private Long mentoringCount;
	private Long studentCount;
	private Boolean reemploymentIdea;
	private Boolean active;
	private Boolean isEmailAlarmAgreed;
	private LocalDateTime createdAt;
	private Set<Long> certificateIds;
	private Set<Long> badgeIds;
	private Set<Long> mentorClassIds;
	private Set<Long> reportIds;
	private Set<Long> conversationIds;
	private Set<Long> mentorCategoryIds;

	public MentorResponse(Long mentorId, String userId, String mentorName, String gender, LocalDate birth, String email,
		String phoneNumber, String address, Long mentoringCount, Long studentCount, Boolean reemploymentIdea,
		Boolean active, Boolean isEmailAlarmAgreed, LocalDateTime createdAt, Set<Long> certificateIds,
		Set<Long> badgeIds,
		Set<Long> mentorClassIds, Set<Long> reportIds, Set<Long> conversationIds, Set<Long> mentorCategoryIds) {
		this.mentorId = mentorId;
		this.userId = userId;
		this.mentorName = mentorName;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.mentoringCount = mentoringCount;
		this.studentCount = studentCount;
		this.reemploymentIdea = reemploymentIdea;
		this.active = active;
		this.isEmailAlarmAgreed = isEmailAlarmAgreed;
		this.createdAt = createdAt;
		this.certificateIds = certificateIds;
		this.badgeIds = badgeIds;
		this.mentorClassIds = mentorClassIds;
		this.reportIds = reportIds;
		this.conversationIds = conversationIds;
		this.mentorCategoryIds = mentorCategoryIds;
	}
}
