package com.MAHD.smart_learning_assistant_service.domain.repository;

import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import reactor.core.publisher.Flux;

import java.util.List;

public interface AiAssistantRepo {

    String userPrompt(String userId, String prompt);

    Flux<String> userPromptStream(String userId, String prompt);

    List<ChatHistory> getUserHistory(String userId);
}