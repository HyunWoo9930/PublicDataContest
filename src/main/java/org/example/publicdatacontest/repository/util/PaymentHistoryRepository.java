package org.example.publicdatacontest.repository.util;

import java.util.List;

import org.example.publicdatacontest.domain.paymentHistory.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
	List<PaymentHistory> findAllByMenteeClass_MenteeId(Long menteeId);
}
