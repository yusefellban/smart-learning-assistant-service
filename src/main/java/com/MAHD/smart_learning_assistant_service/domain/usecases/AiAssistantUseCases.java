package com.MAHD.smart_learning_assistant_service.domain.usecases;

import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.domain.repository.AiAssistantRepo;

import reactor.core.publisher.Flux;

import java.util.List;

/// this is a {@code dd}
public class AiAssistantUseCases {
    private final AiAssistantRepo aiAssistantRepo;

    public AiAssistantUseCases(AiAssistantRepo chatHistoryRepository) {
        this.aiAssistantRepo = chatHistoryRepository;
    }

    public String ask(String userId, String prompt) {
        return aiAssistantRepo.userPrompt(userId, prompt);
    }


    public Flux<String> askWithStream(String userId, String prompt) {
        return aiAssistantRepo.userPromptStream(userId, prompt);
    }

    List<ChatHistory> getUserHistory(String userId) {
        return aiAssistantRepo.getUserHistory(userId);
    }


}
