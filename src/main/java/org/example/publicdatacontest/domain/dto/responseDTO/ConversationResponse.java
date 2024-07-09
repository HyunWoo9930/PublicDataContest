package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConversationResponse {
	private Long conversationId;
	private String mentorName;
	private String menteeName;
	private LocalDateTime startDate;

	public ConversationResponse(Long conversationId, String mentorName, String menteeName, LocalDateTime startDate) {
		this.conversationId = conversationId;
		this.mentorName = mentorName;
		this.menteeName = menteeName;
		this.startDate = startDate;
	}
}
