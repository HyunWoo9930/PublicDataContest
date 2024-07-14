package org.example.publicdatacontest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.example.publicdatacontest.domain.chat.Chat;
import org.example.publicdatacontest.domain.chat.Conversation;
import org.example.publicdatacontest.domain.chat.PaymentStatusHistory;
import org.example.publicdatacontest.domain.dto.requestDTO.ChattingRequest;
import org.example.publicdatacontest.domain.dto.requestDTO.ConversationUpdatePaymentStatusRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.ChatDetailResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.ChatResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.ConversationResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.PaymentStatusHistoryResponse;
import org.example.publicdatacontest.domain.util.PaymentStatus;
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

	public ChatResponse makeChat(UserDetails userDetails, ChattingRequest chattingRequest) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("계정이 만료되었습니다.");
		}
		Conversation conversation = conversationRepository.findById(chattingRequest.getConversationId())
			.orElseThrow(() -> new NotFoundException("Conversation Not Found"));

		Long senderId;
		String senderType;
		String receiverType;
		Long receiverId;
		String senderName;
		String receiverName;

		if (mentorRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			senderType = "mentor";
			senderId = conversation.getMentor().getMentorId();
			senderName = conversation.getMentor().getMentorName();
			receiverId = conversation.getMentee().getMenteeId();
			receiverType = "mentee";
			receiverName = conversation.getMentee().getMenteeName();
		} else if (menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			senderType = "mentee";
			senderId = conversation.getMentee().getMenteeId();
			senderName = conversation.getMentee().getMenteeName();
			receiverId = conversation.getMentor().getMentorId();
			receiverType = "mentor";
			receiverName = conversation.getMentor().getMentorName();
		} else {
			throw new NotFoundException("User Not Found");
		}

		if (senderId == null || receiverId == null) {
			throw new NotFoundException("User Not Found");
		}

		Chat chat = new Chat(
			conversation,
			senderId,
			senderType,
			senderName,
			receiverId,
			receiverType,
			receiverName,
			chattingRequest.getContent(),
			LocalDateTime.now()
		);

		chatRepository.save(chat);
		return new ChatResponse(chat);
	}

	public List<ConversationResponse> getChatList(UserDetails userDetails) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("계정이 만료되었습니다.");
		}
		if (mentorRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			Long mentorId = mentorRepository.findByUserId(userDetails.getUsername()).get().getMentorId();
			List<Conversation> conversations = conversationRepository.findAllByMentorMentorId(mentorId);
			return getConversationResponses(conversations);
		} else if (menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			Long menteeId = menteeRepository.findByUserId(userDetails.getUsername()).get().getMenteeId();
			List<Conversation> conversations = conversationRepository.findAllByMenteeMenteeId(menteeId);
			return getConversationResponses(conversations);
		} else {
			throw new NotFoundException("User Not Found");
		}
	}

	private List<ConversationResponse> getConversationResponses(List<Conversation> conversations) {
		return conversations.stream()
			.map(ConversationResponse::new)
			.collect(Collectors.toList());
	}

	public ChatDetailResponse getChatDetail(UserDetails userDetails, Long conversationId) {
		if (!userDetails.isAccountNonExpired()) {
			throw new RuntimeException("계정이 만료되었습니다.");
		}
		Conversation conversation = conversationRepository.findById(conversationId)
			.orElseThrow(() -> new NotFoundException("conversation이 없습니다."));

		if (mentorRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			if (!conversation.getMentor()
				.getMentorId()
				.equals(mentorRepository.findByUserId(userDetails.getUsername()).get().getMentorId())) {
				throw new RuntimeException("권한이 없습니다.");
			}
		} else if (menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			if (!conversation.getMentee()
				.getMenteeId()
				.equals(menteeRepository.findByUserId(userDetails.getUsername()).get().getMenteeId())) {
				throw new RuntimeException("권한이 없습니다.");
			}
		}

		List<ChatResponse> chatResponses = chatRepository.findAllByConversationConversationId(conversationId).stream()
			.map(ChatResponse::new)
			.collect(Collectors.toList());

		List<PaymentStatusHistoryResponse> paymentStatusHistoryResponses = conversation.getPaymentStatusHistories()
			.stream()
			.map(paymentStatusHistory -> new PaymentStatusHistoryResponse(
				paymentStatusHistory.getId(),
				conversation.getConversationId(),
				paymentStatusHistory.getPaymentStatus(),
				paymentStatusHistory.getTimestamp(),
				paymentStatusHistory.getSender()
			))
			.toList();

		return new ChatDetailResponse(chatResponses, paymentStatusHistoryResponses);
	}

	public void updatePaymentStatus(ConversationUpdatePaymentStatusRequest conversationUpdatePaymentStatusRequest) {
		Conversation conversation = conversationRepository.findById(
				conversationUpdatePaymentStatusRequest.getConversationId())
			.orElseThrow(() -> new NotFoundException("conversation이 없습니다."));

		if (conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.PAYMENT_COMPLETED) {
			conversation.addPaymentStatusHistory(
				new PaymentStatusHistory(conversation, conversationUpdatePaymentStatusRequest.getPaymentStatus(),
					LocalDateTime.now(), "mentee"));
		} else if (conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.PAYMENT_REQUESTED) {
			conversation.addPaymentStatusHistory(
				new PaymentStatusHistory(conversation, conversationUpdatePaymentStatusRequest.getPaymentStatus(),
					LocalDateTime.now(), "mentor"));
		} else if (conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.DAILY_MENTORING_STARTED
			|| conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.DAILY_MENTORING_ENDED
			|| conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.FINAL_MENTORING_ENDED) {
			conversation.addPaymentStatusHistory(
				new PaymentStatusHistory(conversation, conversationUpdatePaymentStatusRequest.getPaymentStatus(),
					LocalDateTime.now(), "mentor"));
		} else {

		}

		conversationRepository.save(conversation);
	}
}
