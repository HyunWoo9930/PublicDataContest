package org.example.publicdatacontest.repository.chat;

import org.example.publicdatacontest.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
