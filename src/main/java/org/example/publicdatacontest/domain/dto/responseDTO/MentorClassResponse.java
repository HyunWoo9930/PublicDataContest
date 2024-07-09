package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorClassResponse {
	private Long classId;
	private String mentorName;
	private String subcategoryName;
	private String categoryName;
	private String name;
	private String location;
	private Long time;
	private Long price;
	private String description;
	private Boolean active;
	private LocalDateTime createdAt;

	public MentorClassResponse(Long classId, String mentorName, String subcategoryName, String categoryName, String name,
		String location, Long time, Long price, String description, Boolean active, LocalDateTime createdAt) {
		this.classId = classId;
		this.mentorName = mentorName;
		this.subcategoryName = subcategoryName;
		this.categoryName = categoryName;
		this.name = name;
		this.location = location;
		this.time = time;
		this.price = price;
		this.description = description;
		this.active = active;
		this.createdAt = createdAt;
	}
}
