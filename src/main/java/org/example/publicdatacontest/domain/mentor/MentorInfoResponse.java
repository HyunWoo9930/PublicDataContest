package org.example.publicdatacontest.domain.mentor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorInfoResponse {
	private MentorDTO mentor;
	private String role;

	public MentorInfoResponse(MentorDTO mentor, String role) {
		this.mentor = mentor;
		this.role = role;
	}
}
