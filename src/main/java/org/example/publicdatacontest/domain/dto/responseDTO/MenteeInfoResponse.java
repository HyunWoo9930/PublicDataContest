package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenteeInfoResponse {
	private MenteeResponse mentee;

	private String role;

	public MenteeInfoResponse(MenteeResponse mentee, String role) {
		this.mentee = mentee;
		this.role = role;
	}
}
