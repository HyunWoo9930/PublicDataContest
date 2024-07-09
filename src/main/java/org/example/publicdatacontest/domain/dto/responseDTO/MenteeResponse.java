package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class MenteeResponse {
	private Long menteeId;
	private String userId;
	private String menteeName;
	private String gender;
	private LocalDate birth;
	private String email;
	private String phoneNumber;
	private String address;
	private Boolean employmentIdea;
	private Boolean active;
	private Boolean isEmailAlarmAgreed;
	private LocalDateTime createdAt;
	private Set<Long> reportIds;
	private Set<Long> conversationIds;
	private Set<Long> menteeClassIds;
	private Set<Long> menteeCategoryIds;
	private Set<Long> reviewIds;

	public MenteeResponse(Long menteeId, String userId, String menteeName, String gender, LocalDate birth, String email,
		String phoneNumber, String address, Boolean employmentIdea, Boolean active, Boolean isEmailAlarmAgreed,
		LocalDateTime createdAt, Set<Long> reportIds, Set<Long> conversationIds, Set<Long> menteeClassIds,
		Set<Long> menteeCategoryIds, Set<Long> reviewIds) {
		this.menteeId = menteeId;
		this.userId = userId;
		this.menteeName = menteeName;
		this.gender = gender;
		this.birth = birth;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.employmentIdea = employmentIdea;
		this.active = active;
		this.isEmailAlarmAgreed = isEmailAlarmAgreed;
		this.createdAt = createdAt;
		this.reportIds = reportIds;
		this.conversationIds = conversationIds;
		this.menteeClassIds = menteeClassIds;
		this.menteeCategoryIds = menteeCategoryIds;
		this.reviewIds = reviewIds;
	}
}
