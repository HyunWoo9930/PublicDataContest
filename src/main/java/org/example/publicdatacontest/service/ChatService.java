package org.example.publicdatacontest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.example.publicdatacontest.domain.chat.Chat;
import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.dto.requestDTO.ChattingRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.ConversationResponse;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.repository.chat.ChatRepository;
import org.example.publicdatacontest.repository.chat.ConversationRepository;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class ChatService {

	private final ChatRepository chatRepository;
	private final MentorRepository mentorRepository;
	private final MenteeRepository menteeRepository;
	private final ConversationRepository conversationRepository;

	public ChatService(ChatRepository chatRepository, MentorRepository mentorRepository,
		MenteeRepository menteeRepository,
		ConversationRepository conversationRepository) {
		this.chatRepository = chatRepository;
		this.mentorRepository = mentorRepository;
		this.menteeRepository = menteeRepository;
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

	public List<ConversationResponse> getChatList(UserDetails userDetails) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("계정이 만료되었습니다.");
		}
		if(mentorRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			Long mentorId = mentorRepository.findByUserId(userDetails.getUsername()).get().getMentorId();
			List<Conversation> conversations = conversationRepository.findAllByMentorMentorId(mentorId);
			return getConversationResponses(conversations);
		} else if(menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			Long menteeId = menteeRepository.findByUserId(userDetails.getUsername()).get().getMenteeId();
			List<Conversation> conversations = conversationRepository.findAllByMenteeMenteeId(menteeId);
			return getConversationResponses(conversations);
		} else {
			throw new NotFoundException("User Not Found");
		}
	}

	private List<ConversationResponse> getConversationResponses(List<Conversation> conversations) {
		List<ConversationResponse> conversationResponses = new ArrayList<>();
		conversations.forEach(conversation -> {
			ConversationResponse conversationResponse = new ConversationResponse(
				conversation.getConversationId(),
				conversation.getMentor().getMentorName(),
				conversation.getMentee().getMenteeName(),
				conversation.getStartDate()
			);
			conversationResponses.add(conversationResponse);
		});
		return conversationResponses;
	}
}
