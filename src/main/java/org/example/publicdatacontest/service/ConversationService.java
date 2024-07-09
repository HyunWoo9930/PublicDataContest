package org.example.publicdatacontest.service;

import java.time.LocalDateTime;

import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.mentee.Mentee;
import org.example.publicdatacontest.domain.mentor.Mentor;
import org.example.publicdatacontest.repository.chat.ConversationRepository;
import org.example.publicdatacontest.repository.mentee.MenteeRepository;
import org.example.publicdatacontest.repository.mentor.MentorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class ConversationService {

	private final ConversationRepository conversationRepository;
	private final MentorRepository mentorRepository;

	private final MenteeRepository menteeRepository;

	public ConversationService(ConversationRepository conversationRepository, MentorRepository mentorRepository,
		MenteeRepository menteeRepository) {
		this.conversationRepository = conversationRepository;
		this.mentorRepository = mentorRepository;
		this.menteeRepository = menteeRepository;
	}

	public Long makeConversation(UserDetails userDetails, Long mentorId) {
		Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(() -> new NotFoundException("mentor가 없습니다."));
		Mentee mentee = menteeRepository.findByUserId(userDetails.getUsername())
			.orElseThrow(() -> new NotFoundException("mentee가 없거나, mentee가 아닙니다."));
		if (conversationRepository.findByMenteeMenteeIdAndMentorMentorId(mentee.getMenteeId(), mentorId).isPresent()) {
			Conversation conversation = conversationRepository.findByMenteeMenteeIdAndMentorMentorId(
				mentee.getMenteeId(), mentorId).get();
			return conversation.getConversationId();
		}
		Conversation conversation = new Conversation(mentor, mentee, LocalDateTime.now());
		Conversation save = conversationRepository.save(conversation);
		Conversation conversation1 = conversationRepository.findById(save.getConversationId())
			.orElseThrow(() -> new NotFoundException("conversation이 없습니다."));
		return conversation1.getConversationId();
	}

}
