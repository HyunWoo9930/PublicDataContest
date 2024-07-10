package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MakeChatResponse {
	Long conversationId;
	Boolean isAlready;

	public MakeChatResponse(Long conversationId, Boolean isAlready) {
		this.conversationId = conversationId;
		this.isAlready = isAlready;
	}
}
