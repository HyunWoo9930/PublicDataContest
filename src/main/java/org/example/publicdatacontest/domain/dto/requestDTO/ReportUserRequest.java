package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportUserRequest {
	private String reportContent;
	private String reportedUserId;

	public ReportUserRequest(String reportContent, String reportedUserId) {
		this.reportContent = reportContent;
		this.reportedUserId = reportedUserId;
	}
}
