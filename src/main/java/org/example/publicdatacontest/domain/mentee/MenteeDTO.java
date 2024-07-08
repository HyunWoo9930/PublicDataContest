package org.example.publicdatacontest.domain.mentee;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class MenteeDTO {
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
}
