package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorInfoResponse {
	private MentorResponse mentor;
	private String role;

	public MentorInfoResponse(MentorResponse mentor, String role) {
		this.mentor = mentor;
		this.role = role;
	}
}
