package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.mentor.MentorClass;

@Getter
@Setter
public class MentorClassResponse {
	private Long classId;
	private String mentorName;
	private Long mentorId;
	private String subcategoryName;
	private String categoryName;
	private String name;
	private String location;
	private Long time;
	private Long price;
	private String description;
	private Boolean active;
	private LocalDateTime createdAt;

	public MentorClassResponse(MentorClass mentorClass) {
		this.classId = mentorClass.getClassId();
		this.mentorName = mentorClass.getMentor().getName();
		this.mentorId = mentorClass.getMentor().getId();
		this.subcategoryName = mentorClass.getSubCategory().getName();
		this.categoryName = mentorClass.getSubCategory().getCategory().getName();
		this.name = mentorClass.getName();
		this.location = mentorClass.getLocation();
		this.time = mentorClass.getTime();
		this.price = mentorClass.getPrice();
		this.description = mentorClass.getDescription();
		this.active = mentorClass.getActive();
		this.createdAt = mentorClass.getCreatedAt();
	}
}
