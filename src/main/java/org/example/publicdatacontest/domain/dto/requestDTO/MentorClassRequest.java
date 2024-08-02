package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MentorClassRequest {
	private Long classId;
	private String name;
	private String location;
	private Long time;
	private Long price;
	private String description;
	private Long subcategoryId;
}
