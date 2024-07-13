package org.example.publicdatacontest.repository.chat;

import org.example.publicdatacontest.domain.chat.PaymentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStatusHistoryRepository extends JpaRepository<PaymentStatusHistory, Long> {
}
