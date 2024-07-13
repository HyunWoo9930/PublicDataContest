package org.example.publicdatacontest.domain.dto.responseDTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {
	private String className;
	private String mentorName;
	private Long price;
	private Long count;
	private Long usedCount;
	private Long remainingCount;
	private LocalDateTime timestamp;

	public PaymentResponse(String className, String mentorName, Long price, Long count, Long usedCount,
		Long remainingCount, LocalDateTime timestamp) {
		this.className = className;
		this.mentorName = mentorName;
		this.price = price;
		this.count = count;
		this.usedCount = usedCount;
		this.remainingCount = remainingCount;
		this.timestamp = timestamp;
	}
}
