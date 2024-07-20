package org.example.publicdatacontest.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.example.publicdatacontest.domain.dto.requestDTO.PaymentRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.PaymentResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentee.MenteeClass;
import org.example.publicdatacontest.domain.mentee.MenteeClassId;
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.paymentHistory.PaymentHistory;
import org.example.publicdatacontest.repository.mentee.MenteeClassRepository;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorClassRepository;
import org.example.publicdatacontest.repository.util.PaymentHistoryRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class PaymentHistoryService {

	private final MentorClassRepository mentorClassRepository;
	private final PaymentHistoryRepository paymentHistoryRepository;
	private final MenteeClassRepository menteeClassRepository;
	private final MenteeRepository menteeRepository;

	public PaymentHistoryService(MentorClassRepository mentorClassRepository,
		PaymentHistoryRepository paymentHistoryRepository, MenteeClassRepository menteeClassRepository,
		MenteeRepository menteeRepository) {
		this.mentorClassRepository = mentorClassRepository;
		this.paymentHistoryRepository = paymentHistoryRepository;
		this.menteeClassRepository = menteeClassRepository;
		this.menteeRepository = menteeRepository;
	}

	public void postPaymentHistory(UserDetails userDetails, PaymentRequest paymentRequest) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("User account is expired");
		}

		Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new NotFoundException("user not found"));

		MentorClass mentorClass = mentorClassRepository.findById(paymentRequest.getClassId())
			.orElseThrow(() -> new NotFoundException("class not found"));

		MenteeClassId menteeClassId = new MenteeClassId(mentorClass.getClassId(), mentee.getId());

		Optional<MenteeClass> menteeClassOptional = menteeClassRepository.findById(menteeClassId);

		MenteeClass menteeClass = menteeClassOptional.map(existingMenteeClass -> {
			existingMenteeClass.setCount(existingMenteeClass.getCount() + paymentRequest.getCount());
			return existingMenteeClass;
		}).orElseGet(() -> {
			MenteeClass newMenteeClass = new MenteeClass();
			newMenteeClass.setClassId(mentorClass.getClassId());
			newMenteeClass.setMenteeId(mentee.getId());
			newMenteeClass.setMentorClass(mentorClass);
			newMenteeClass.setMentee(mentee);
			newMenteeClass.setCount(paymentRequest.getCount());
			newMenteeClass.setUsedCount(0L);
			newMenteeClass.setTimestamp(LocalDateTime.now());
			return newMenteeClass;
		});

		menteeClass = menteeClassRepository.save(menteeClass);

		PaymentHistory paymentHistory = new PaymentHistory();
		paymentHistory.setMenteeClass(menteeClass);
		paymentHistory.setPaymentDate(LocalDateTime.now());
		paymentHistory.setAmount(paymentRequest.getCount());
		paymentHistory.setPrice(mentorClass.getPrice() * paymentRequest.getCount());

		paymentHistoryRepository.save(paymentHistory);

		if (menteeClass.getPaymentHistories() == null) {
			menteeClass.setPaymentHistories(new HashSet<>());
		}
		menteeClass.getPaymentHistories().add(paymentHistory);
	}

	public List<PaymentResponse> getPaymentHistory(UserDetails userDetails) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("User account is expired");
		}

		Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new NotFoundException("user not found"));

		return paymentHistoryRepository.findAllByMenteeClass_MenteeId(
			mentee.getId()).stream().map(paymentHistory -> {
			MentorClass mentorClass = paymentHistory.getMenteeClass().getMentorClass();
			return new PaymentResponse(
				mentorClass.getName(),
				mentorClass.getMentor().getName(),
				paymentHistory.getPrice(),
				paymentHistory.getAmount(),
				paymentHistory.getMenteeClass().getUsedCount(),
				paymentHistory.getAmount() - paymentHistory.getMenteeClass().getUsedCount(),
				paymentHistory.getPaymentDate()
			);
		}).toList();
	}

}
