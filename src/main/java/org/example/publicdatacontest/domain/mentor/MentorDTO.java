package org.example.publicdatacontest.domain.mentor;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class MentorDTO {
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
}
