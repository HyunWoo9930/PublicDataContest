package org.example.publicdatacontest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.publicdatacontest.domain.chat.PaymentStatusHistory;
import org.example.publicdatacontest.domain.dto.requestDTO.ReviewRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.ReviewResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.mentee.MenteeClassId;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.util.Review;
import org.example.publicdatacontest.repository.chat.PaymentStatusHistoryRepository;
import org.example.publicdatacontest.repository.mentee.MenteeClassRepository;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorClassRepository;
import org.example.publicdatacontest.repository.util.ReviewRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenteeRepository menteeRepository;
    private final MenteeClassRepository menteeClassRepository;
    private final MentorClassRepository mentorClassRepository;
    private final PaymentStatusHistoryRepository paymentStatusHistoryRepository;

    public String writeReview(ReviewRequest request, UserDetails userDetails) throws IllegalAccessException {

        Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("user not found"));

        MentorClass mentorClass = mentorClassRepository.findByClassId(request.getClassId())
                .orElseThrow(() -> new NotFoundException("class not found"));

        MenteeClassId menteeClassId = new MenteeClassId(request.getClassId(), mentee.getMenteeId());

        Optional<MenteeClass> menteeClassOptional = menteeClassRepository.findById(menteeClassId);

        if (menteeClassOptional.isEmpty()) {
            throw new RuntimeException("class 수강 전적이 없으므로 리뷰 작성이 불가합니다.");
        }

        Optional<PaymentStatusHistory> paymentStatusHistories = paymentStatusHistoryRepository.findById(request.getPaymentStatusHistoryId());

        if (paymentStatusHistories.isEmpty()) {
            throw new NotFoundException("paymentStatusHistory not found");
        }

        PaymentStatusHistory paymentStatusHistory = paymentStatusHistories.get();

        if (!paymentStatusHistory.getReviewCheck()) {
            throw new IllegalAccessException("멘토링이 완료되지 않았으므로 리뷰를 작성할 수 없습니다.");
        }

        reviewRepository.save(Review.of(mentorClass, mentee, request.getRating(), request.getComment()));

        paymentStatusHistory.setReviewCheck(false);

        return "review saved successfully";
    }

    public ReviewResponse reviewDetail(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("review not found"));

        return new ReviewResponse(review);
    }

    public List<ReviewResponse> getReviewListOfClass(Long classId) {
        MentorClass mentorClass = mentorClassRepository.findByClassId(classId)
                .orElseThrow(() -> new NotFoundException("class not found"));

        return mentorClass.getReviews().stream()
                .map(ReviewResponse::new)
                .toList();
    }

    public List<ReviewResponse> getReviewListOfMentee(UserDetails userDetails) {
        Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("user not found"));

        return mentee.getReviews().stream()
                .map(ReviewResponse::new)
                .toList();
    }
}
