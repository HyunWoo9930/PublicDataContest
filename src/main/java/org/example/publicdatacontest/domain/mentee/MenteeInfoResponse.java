package org.example.publicdatacontest.domain.mentee;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenteeInfoResponse {
	private MenteeDTO mentee;

	private String role;

	public MenteeInfoResponse(MenteeDTO mentee, String role) {
		this.mentee = mentee;
		this.role = role;
	}
}
