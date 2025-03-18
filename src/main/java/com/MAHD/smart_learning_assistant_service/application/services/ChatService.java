package com.MAHD.smart_learning_assistant_service.application.services;

import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.domain.repository.AiAssistantRepo;
import com.MAHD.smart_learning_assistant_service.domain.repository.FeedbackRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {


    AiAssistantRepo aiAssistantRepo;
    FeedbackRepo feedbackRepo;

    public ChatService(AiAssistantRepo aiAssistantRepo, FeedbackRepo feedbackRepo) {
        this.aiAssistantRepo = aiAssistantRepo;
        this.feedbackRepo = feedbackRepo;
    }

    public String ask(String userId, String prompt) {
        return aiAssistantRepo.userPrompt(userId, prompt);
    }

    public Flux<String> askWithStream(String userId, String prompt) {
        return aiAssistantRepo.userPromptStream(userId, prompt);

    }

    public List<ChatHistory> getHistory(String userId) {
        return aiAssistantRepo.getUserHistory(userId);

    }

    public String submitFeedback(String userId, String feedback) {
        return feedbackRepo.submitFeedback(userId, feedback);
    }

    public Optional<UserFeedback> getFeedback(String userId) {
        return feedbackRepo.getFeedbackByUserId(userId);

    }

    public List<UserFeedback> getAllFeedbacks() {
        return feedbackRepo.getAllFeedbacks();
    }


}