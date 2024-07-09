package org.example.publicdatacontest.service;

import java.time.LocalDateTime;

import org.example.publicdatacontest.domain.chat.Chat;
import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.dto.requestDTO.ChattingRequest;
import org.example.publicdatacontest.repository.chat.ChatRepository;
import org.example.publicdatacontest.repository.chat.ConversationRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class ChatService {

	private final ChatRepository chatRepository;
	private final MentorRepository mentorRepository;
	private final ConversationRepository conversationRepository;

	public ChatService(ChatRepository chatRepository, MentorRepository mentorRepository,
		ConversationRepository conversationRepository) {
		this.chatRepository = chatRepository;
		this.mentorRepository = mentorRepository;
		this.conversationRepository = conversationRepository;
	}

	public void makeChat(UserDetails userDetails, ChattingRequest chattingRequest) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("계정이 만료되었습니다.");
		}
		String senderType = mentorRepository.findByUserId(userDetails.getUsername()).isPresent() ? "mentor" : "mentee";
		String receiverType = senderType.equals("mentor") ? "mentee" : "mentor";

		Conversation conversation = conversationRepository.findById(chattingRequest.getConversationId())
			.orElseThrow(() -> new NotFoundException("conversation이 없습니다."));

		Chat chat = new Chat(
			conversation,
			chattingRequest.getSenderId(),
			senderType,
			chattingRequest.getReceiverId(),
			receiverType,
			chattingRequest.getContent(),
			LocalDateTime.now()
		);

		chatRepository.save(chat);
	}
}
