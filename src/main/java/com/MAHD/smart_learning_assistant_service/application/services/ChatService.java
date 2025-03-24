package com.MAHD.smart_learning_assistant_service.application.services;

import com.MAHD.smart_learning_assistant_service.application.dto.AskResponse;
import com.MAHD.smart_learning_assistant_service.application.dto.ChatHistoryResponse;
import com.MAHD.smart_learning_assistant_service.domain.repository.AiAssistantRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ChatService is the main service class that acts as an intermediary between the controllers
 * and the repository layer for handling chat interactions.
 * <p>
 * This service interacts with:
 * <ul>
 *     <li>{@link AiAssistantRepo} - For processing user prompts and retrieving chat history.</li>
 * </ul>
 * </p>
 *
 * <h3>Design Considerations:</h3>
 * <ul>
 *     <li>Supports both synchronous and reactive interactions with the AI Assistant.</li>
 * </ul>
 */
@Service
public class ChatService {

    AiAssistantRepo aiAssistantRepo;

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public ChatService(AiAssistantRepo aiAssistantRepo) {
        this.aiAssistantRepo = aiAssistantRepo;

    }

    public ResponseEntity<AskResponse> ask(String userId, String prompt) {

        Optional<String> response = aiAssistantRepo.userPrompt(userId, prompt);

        return response.map(AskResponse::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .build());

    }

    public Flux<String> askWithStream(String userId, String prompt) {
        return aiAssistantRepo.userPromptStream(userId, prompt);

    }

    public ResponseEntity<List<ChatHistoryResponse>> getHistory(String userId) {
        return aiAssistantRepo.getUserHistory(userId)
                .map(historyList -> historyList.stream()
                        .map(ChatHistoryResponse::toChatHistoryResponse)
                        .toList())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    /**
     * Deletes chat history older than 2 days.
     * Runs every day at midnight.
     */
    @Scheduled(cron = "0 0 0 * * ?") // Runs at 12:00 AM every day
    public void cleanOldChats() {
        LocalDateTime expiryTime = LocalDateTime.now().minusDays(2);
        int deletedCount = aiAssistantRepo.deleteOldChats(expiryTime);
        logger.info("Deleted {} old chats", deletedCount);
    }
}