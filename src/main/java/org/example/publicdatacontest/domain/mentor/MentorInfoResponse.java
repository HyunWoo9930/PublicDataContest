package org.example.publicdatacontest.domain.mentor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorInfoResponse {
	private Mentor mentor;
	private String role;

	public MentorInfoResponse(Mentor mentor, String role) {
		this.mentor = mentor;
		this.role = role;
	}
}
