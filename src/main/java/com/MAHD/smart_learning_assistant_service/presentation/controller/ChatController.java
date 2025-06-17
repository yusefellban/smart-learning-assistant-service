package com.MAHD.smart_learning_assistant_service.presentation.controller;

import com.MAHD.smart_learning_assistant_service.application.dto.AskResponse;
import com.MAHD.smart_learning_assistant_service.application.dto.ChatHistoryResponse;
import com.MAHD.smart_learning_assistant_service.application.dto.FeedbackResponse;
import com.MAHD.smart_learning_assistant_service.application.services.FeedBackService;
import com.MAHD.smart_learning_assistant_service.application.services.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;


@RestController
@RequestMapping("/ai-assistant")
@Tag(name = "Chat Bot", description = "Smart Learning Assistant Service")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final FeedBackService feedBackService;


    @PostMapping("/ask")
    @Operation(summary = "Ask AI assistant", description = "Sends a user query to the chatbot and returns a response.")
    public ResponseEntity<AskResponse> ask(@RequestParam String userId, @RequestParam String prompt) {
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwhiiiii it is w");
        return chatService.ask(userId, prompt);
    }


    @PostMapping("/ask/stream")
    @Operation(summary = "Ask AI Assistant(Streaming)", description = "Sends a user query and returns a streamed AI response.")
    public Flux<String> askWithStream(@RequestParam String userId, @RequestParam String prompt) {
        return chatService.askWithStream(userId, prompt);

    }

    @GetMapping("/chat/history/{userId}")
    @Operation(summary = "Get User Chat History", description = "Retrieves the chat history for a specific user.")
    public ResponseEntity<List<ChatHistoryResponse>> getHistory(@PathVariable String userId) {
        return chatService.getHistory(userId);
    }


    @PutMapping("/feedback/submit")
    @Operation(summary = "Submit User Feedback", description = "Submits user feedback for a chatbot response.")
    public ResponseEntity<String> submitFeedback(@RequestParam String userId, @RequestParam String feedback) {
        return feedBackService.submitFeedback(userId, feedback);

    }

    @GetMapping("/feedback/user/{userId}")
    @Operation(summary = "Get User Feedback", description = "Retrieves feedback submitted by a specific user.")
    public ResponseEntity<FeedbackResponse> getFeedback(@PathVariable String userId) {
        return feedBackService.getFeedback(userId);

    }

    /// not use case in the system
    @GetMapping("/feedback/all")
    @Operation(summary = "Get All Feedback For All Users", description = "Fetches all user feedback submitted to the system.")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedbacks() {
        return ResponseEntity.ok(feedBackService.getAllFeedbacks());
    }

}
