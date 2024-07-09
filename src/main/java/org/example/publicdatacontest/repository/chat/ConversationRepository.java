package org.example.publicdatacontest.repository.chat;

import java.util.List;
import java.util.Optional;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
	List<Conversation> findAllByMentorMentorId(Long mentorId);

	List<Conversation> findAllByMenteeMenteeId(Long menteeId);

	Optional<Conversation> findByMenteeMenteeIdAndMentorMentorId(Long menteeId, Long mentorId);
}
