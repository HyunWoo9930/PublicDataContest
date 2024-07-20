package org.example.publicdatacontest.domain.dto.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.publicdatacontest.domain.util.Review;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {

    private Long reviewId;
    private String className;
    private String menteeName;
    private Long rating;
    private String comment;
    private LocalDateTime timestamp;

    public ReviewResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.className = review.getMentorClass().getName();
        this.menteeName = review.getMentee().getName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.timestamp = review.getTimestamp();
    }
}
