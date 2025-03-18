package com.MAHD.smart_learning_assistant_service.presentation.controller;

import com.MAHD.smart_learning_assistant_service.application.dto.AskResponse;
import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.application.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Optional;
import java.util.List;

/**
 * ChatController handles API requests for the Smart Learning Assistant Service.
 * This controller provides endpoints for interacting with the AI chatbot, retrieving chat history,
 * submitting and retrieving user feedback, and supporting real-time AI responses through streaming.
 * Endpoints:
 * - POST /AI/ask           → Sends a user query to the chatbot and returns a response.
 * - POST /AI/ask-stream    → Sends a user query and returns a streamed AI response.
 * - GET  /AI/history/{userId} → Retrieves the chat history for a specific user.
 * - PUT  /AI/feedback      → Submits feedback for the chatbot's response.
 * - GET  /AI/feedback/{userId} → Retrieves feedback submitted by a specific user.
 * - GET  /AI/feedback/all  → Fetches all user feedback.
 * - GET  /AI/ask/all       → Retrieves all chat interactions.
 * This controller interacts with the ChatService to process user requests and manage data storage.
 * The chatbot's responses are handled synchronously and asynchronously using Reactor's Flux for streaming.
 *
 * @author Yousef El-llban
 * @version 1.0
 * @since 2025-03-15
 */

@RestController
@RequestMapping("/ai-assistant")
@Tag(name = "Chat Bot", description = "Smart Learning Assistant Service")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ask")
    @Operation(summary = "Ask AI assistant", description = "Sends a user query to the chatbot and returns a response.")
    public AskResponse ask(@RequestParam String userId, @RequestParam String prompt) {
        return new AskResponse(chatService.ask(userId, prompt));
    }


    @PostMapping("/ask/stream")
    @Operation(summary = "Ask AI Assistant(Streaming)", description = "Sends a user query and returns a streamed AI response.")
    public Flux<String> askWithStream(@RequestParam String userId, @RequestParam String prompt) {
        return chatService.askWithStream(userId, prompt);
    }

    @GetMapping("/chat/history/{userId}")
    @Operation(summary = "Get User Chat History", description = "Retrieves the chat history for a specific user.")
    public List<ChatHistory> getHistory(@PathVariable String userId) {
        return chatService.getHistory(userId);
    }


    @PutMapping("/user/feedback")
    @Operation(summary = "Submit User Feedback", description = "Submits user feedback for a chatbot response.")
    public ResponseEntity<String> submitFeedback(@RequestParam String userId, @RequestParam String feedback) {
        String response = chatService.submitFeedback(userId, feedback);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/user/feedback/{userId}")
    @Operation(summary = "Get User Feedback", description = "Retrieves feedback submitted by a specific user.")
    public ResponseEntity<UserFeedback> getFeedback(@PathVariable String userId) {
        Optional<UserFeedback> feedback = chatService.getFeedback(userId);
        return feedback.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /// not use case in the system
    @GetMapping("/users/feedback/all")
    @Operation(summary = "Get All Feedback For All Users", description = "Fetches all user feedback submitted to the system.")
    public List<UserFeedback> getAllFeedbacks() {
        return chatService.getAllFeedbacks();
    }


}
