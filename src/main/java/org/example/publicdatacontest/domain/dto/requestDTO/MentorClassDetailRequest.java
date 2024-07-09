package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MentorClassDetailRequest {
	private Long classId;

	public MentorClassDetailRequest(Long classId) {
		this.classId = classId;
	}
}
