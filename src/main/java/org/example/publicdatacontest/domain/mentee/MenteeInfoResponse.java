package org.example.publicdatacontest.domain.mentee;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenteeInfoResponse {
	private Mentee mentee;

	private String role;

	public MenteeInfoResponse(Mentee mentee, String role) {
		this.mentee = mentee;
		this.role = role;
	}
}
