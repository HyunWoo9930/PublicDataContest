package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.mentor.*;
import org.example.publicdatacontest.domain.util.Reports;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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

	private MentorCategoryResponse mentorCategoryNames;

	public MentorResponse(Mentor mentor) {
		this.mentorId = mentor.getMentorId();
		this.userId = mentor.getUserId();
		this.mentorName = mentor.getMentorName();
		this.gender = mentor.getGender();
		this.birth = mentor.getBirth();
		this.email = mentor.getEmail();
		this.phoneNumber = mentor.getPhoneNumber();
		this.address = mentor.getAddress();
		this.mentoringCount = mentor.getMentoringCount();
		this.studentCount = mentor.getStudentCount();
		this.reemploymentIdea = mentor.getReemploymentIdea();
		this.active = mentor.getActive();
		this.isEmailAlarmAgreed = mentor.getIsEmailAlarmAgreed();
		this.createdAt = mentor.getCreatedAt();
		this.certificateIds = mentor.getCertificates().stream().map(MentorCertificate::getCertificateId).collect(Collectors.toSet());
		this.badgeIds = mentor.getBadges().stream().map(MentorBadge::getBadgeId).collect(Collectors.toSet());
		this.mentorClassIds = mentor.getMentorClasses().stream().map(MentorClass::getClassId).collect(Collectors.toSet());
		this.reportIds = mentor.getReports().stream().map(Reports::getReportId).collect(Collectors.toSet());
		this.conversationIds = mentor.getConversations().stream().map(Conversation::getConversationId).collect(Collectors.toSet());
		this.mentorCategoryNames = new MentorCategoryResponse(mentor.getMentorCategories());
	}
}
