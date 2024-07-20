package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeCategory;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.util.Reports;
import org.example.publicdatacontest.domain.util.Review;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
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
	private String payment;
	private String image;
	private LocalDateTime createdAt;
	private Set<Long> reportIds;
	private Set<Long> conversationIds;
	private Set<Long> menteeClassIds;
	private Set<Long> reviewIds;

	private List<MenteeCategoryResponse> menteeCategoryNames;

	public MenteeResponse(Mentee mentee) {
		this.menteeId = mentee.getId();
		this.userId = mentee.getUserId();
		this.menteeName = mentee.getName();
		this.gender = mentee.getGender();
		this.birth = mentee.getBirth();
		this.email = mentee.getEmail();
		this.phoneNumber = mentee.getPhoneNumber();
		this.address = mentee.getAddress();
		this.employmentIdea = mentee.getEmploymentIdea();
		this.active = mentee.getActive();
		this.isEmailAlarmAgreed = mentee.getIsEmailAlarmAgreed();
		this.payment = mentee.getPaymentMethod();
		this.image = mentee.getProfilePicture() != null ? Base64.getEncoder().encodeToString(mentee.getProfilePicture()) : null;
		this.createdAt = mentee.getCreatedAt();
		this.reportIds = mentee.getReports().stream().map(Reports::getReportId).collect(Collectors.toSet());
		this.conversationIds = mentee.getConversations().stream().map(Conversation::getConversationId).collect(Collectors.toSet());
		this.menteeClassIds = mentee.getMenteeClasses().stream().map(MenteeClass::getClassId).collect(Collectors.toSet());
		this.reviewIds = mentee.getReviews().stream().map(Review::getReviewId).collect(Collectors.toSet());
		this.menteeCategoryNames = mentee.getMenteeCategories().stream().map(MenteeCategoryResponse::new).toList();
	}
}
