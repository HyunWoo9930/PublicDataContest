package org.example.publicdatacontest.controller;

import java.util.List;

import org.example.publicdatacontest.domain.dto.requestDTO.ChattingRequest;
import org.example.publicdatacontest.domain.dto.responseDTO.ChatResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.ConversationResponse;
import org.example.publicdatacontest.domain.dto.responseDTO.MakeChatResponse;
import org.example.publicdatacontest.service.ChatService;
import org.example.publicdatacontest.service.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

	private final ConversationService conversationService;

	private final ChatService chatService;

	public ChatController(ConversationService conversationService, ChatService chatService) {
		this.conversationService = conversationService;
		this.chatService = chatService;
	}

	@PostMapping("/make_chat")
	public ResponseEntity<?> makeChat(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam("mentorId") Long mentorId
	) {
		try {
			MakeChatResponse makeChatResponse = conversationService.makeConversation(userDetails, mentorId);
			return ResponseEntity.ok(makeChatResponse);
		} catch (NotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@PostMapping("/chatting")
	public ResponseEntity<?> chatting(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestBody ChattingRequest chattingRequest
	) {
		try {
			ChatResponse chatResponse = chatService.makeChat(userDetails, chattingRequest);
			return ResponseEntity.ok(chatResponse);
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@GetMapping("/chat_list")
	public ResponseEntity<?> chatList(
		@AuthenticationPrincipal UserDetails userDetails
	) {
		try {
			List<ConversationResponse> chatList = chatService.getChatList(userDetails);
			return ResponseEntity.ok(chatList);
		} catch (NotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}

	@GetMapping("/chatting_detail")
	@Operation(summary = "채팅방 들어갔을때 채팅 내용 가져오기")
	public ResponseEntity<?> chattingDetail(
		@AuthenticationPrincipal UserDetails userDetails,
		@RequestParam("conversationId") Long conversationId
	) {
		try {
			List<ChatResponse> chatList = chatService.getChatDetail(userDetails, conversationId);
			return ResponseEntity.ok(chatList);
		} catch (NotFoundException e) {
			return ResponseEntity.status(404).body(e.getMessage());
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		}
	}
}
