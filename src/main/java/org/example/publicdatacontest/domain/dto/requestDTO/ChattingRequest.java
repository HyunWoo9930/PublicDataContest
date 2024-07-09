package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChattingRequest {
	private Long conversationId;
	private String content;

	public ChattingRequest(Long conversationId, String content) {
		this.conversationId = conversationId;
		this.content = content;
	}
}
