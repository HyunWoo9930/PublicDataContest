package org.example.publicdatacontest.domain.dto.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    private Long rating;
    private String comment;
    private Long classId;
    private Long paymentStatusHistoryId;
}
