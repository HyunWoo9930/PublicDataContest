package org.example.publicdatacontest.repository.chat;

import org.example.publicdatacontest.domain.chat.PaymentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentStatusHistoryRepository extends JpaRepository<PaymentStatusHistory, Long> {
}
