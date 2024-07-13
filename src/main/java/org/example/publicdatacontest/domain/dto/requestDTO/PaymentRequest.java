package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
	private Long classId;
	private Long count;

	public PaymentRequest(Long classId, Long count) {
		this.classId = classId;
		this.count = count;
	}
}
