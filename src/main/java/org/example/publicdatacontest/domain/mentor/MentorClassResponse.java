package org.example.publicdatacontest.domain.mentor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorClassResponse {
	private String name;
	private String location;
	private Long time;
	private Long price;
	private String description;

	public MentorClassResponse(String name, String location, Long time, Long price, String description) {
		this.name = name;
		this.location = location;
		this.time = time;
		this.price = price;
		this.description = description;
	}
}
