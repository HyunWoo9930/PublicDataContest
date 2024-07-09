package org.example.publicdatacontest.repository.chat;

import java.util.List;

import org.example.publicdatacontest.domain.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	List<Chat> findAllByConversationConversationId(Long conversationId);
}
