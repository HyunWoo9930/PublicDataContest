package org.example.publicdatacontest.repository.chat;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
