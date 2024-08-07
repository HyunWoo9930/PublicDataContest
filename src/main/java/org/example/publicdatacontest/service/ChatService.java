package org.example.publicdatacontest.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
import org.example.publicdatacontest.domain.mentor.MentorClass;
import org.example.publicdatacontest.domain.util.PaymentStatus;
import org.example.publicdatacontest.repository.chat.ChatRepository;
import org.example.publicdatacontest.repository.chat.ConversationRepository;
import org.example.publicdatacontest.repository.chat.PaymentStatusHistoryRepository;
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

	private final PaymentStatusHistoryRepository paymentStatusHistoryRepository;

	public ChatService(ChatRepository chatRepository, MentorRepository mentorRepository,
		MenteeRepository menteeRepository,
		ConversationRepository conversationRepository, PaymentStatusHistoryRepository paymentStatusHistoryRepository) {
		this.chatRepository = chatRepository;
		this.mentorRepository = mentorRepository;
		this.menteeRepository = menteeRepository;
		this.conversationRepository = conversationRepository;
		this.paymentStatusHistoryRepository = paymentStatusHistoryRepository;
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
			senderId = conversation.getMentor().getId();
			senderName = conversation.getMentor().getName();
			receiverId = conversation.getMentee().getId();
			receiverType = "mentee";
			receiverName = conversation.getMentee().getName();
		} else if (menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			senderType = "mentee";
			senderId = conversation.getMentee().getId();
			senderName = conversation.getMentee().getName();
			receiverId = conversation.getMentor().getId();
			receiverType = "mentor";
			receiverName = conversation.getMentor().getName();
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
			Long mentorId = mentorRepository.findByUserId(userDetails.getUsername()).get().getId();
			List<Conversation> conversations = conversationRepository.findAllByMentorId(mentorId);
			return getConversationResponses(conversations);
		} else if (menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			Long menteeId = menteeRepository.findByUserId(userDetails.getUsername()).get().getId();
			List<Conversation> conversations = conversationRepository.findAllByMenteeId(menteeId);
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
				.getId()
				.equals(mentorRepository.findByUserId(userDetails.getUsername()).get().getId())) {
				throw new RuntimeException("권한이 없습니다.");
			}
		} else if (menteeRepository.findByUserId(userDetails.getUsername()).isPresent()) {
			if (!conversation.getMentee()
				.getId()
				.equals(menteeRepository.findByUserId(userDetails.getUsername()).get().getId())) {
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
				paymentStatusHistory.getSender(),
				paymentStatusHistory.getRequestedClassId(),
				paymentStatusHistory.getReviewCheck()
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
					LocalDateTime.now(), "mentee", 0L, false));
		} else if (conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.PAYMENT_REQUESTED) {
			conversation.addPaymentStatusHistory(
				new PaymentStatusHistory(conversation, conversationUpdatePaymentStatusRequest.getPaymentStatus(),
					LocalDateTime.now(), "mentor", 0L, false));
		} else if (conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.DAILY_MENTORING_STARTED
			|| conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.DAILY_MENTORING_ENDED) {
			conversation.addPaymentStatusHistory(
				new PaymentStatusHistory(conversation, conversationUpdatePaymentStatusRequest.getPaymentStatus(),
					LocalDateTime.now(), "mentor", 0L, false));
		} else if (conversationUpdatePaymentStatusRequest.getPaymentStatus() == PaymentStatus.FINAL_MENTORING_ENDED) {
			conversation.addPaymentStatusHistory(
				new PaymentStatusHistory(conversation, conversationUpdatePaymentStatusRequest.getPaymentStatus(),
					LocalDateTime.now(), "mentor", 0L, true));
		}

		conversationRepository.save(conversation);
	}

	public void updateReceivedClassId(Long paymentStatusId, Long classId) {
		PaymentStatusHistory paymentStatusHistory = paymentStatusHistoryRepository.findById(paymentStatusId)
			.orElseThrow(() -> new NotFoundException("paymentStatus가 없습니다."));

		Set<MentorClass> mentorClasses = paymentStatusHistory.getConversation().getMentor().getMentorClasses();
		if (mentorClasses.stream().noneMatch(mentorClass -> mentorClass.getClassId().equals(classId))) {
			throw new NotFoundException("class가 없습니다.");
		}

		paymentStatusHistory.setRequestedClassId(classId);
		paymentStatusHistoryRepository.save(paymentStatusHistory);
	}
}
