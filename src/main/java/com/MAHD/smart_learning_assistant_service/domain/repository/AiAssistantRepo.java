package com.MAHD.smart_learning_assistant_service.domain.repository;

import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AiAssistantRepo {

   Optional<String> userPrompt(String userId, String prompt);

    Flux<String> userPromptStream(String userId, String prompt);

    Optional<List<ChatHistory>> getUserHistory(String userId);

    int deleteOldChats(LocalDateTime expiryTime);
}