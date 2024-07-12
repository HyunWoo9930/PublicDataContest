package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import org.example.publicdatacontest.domain.chat.Conversation;

@Setter
@Getter
public class ConversationResponse {
	private Long conversationId;
	private String mentorName;
	private String menteeName;
	private LocalDateTime startDate;

	public ConversationResponse(Conversation conversation) {
		this.conversationId = conversation.getConversationId();
		this.mentorName = conversation.getMentor().getMentorName();
		this.menteeName = conversation.getMentee().getMenteeName();
		this.startDate = conversation.getStartDate();
	}
}
