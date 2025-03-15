package com.MAHD.smart_learning_assistant_service.service;

import com.MAHD.smart_learning_assistant_service.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.repo.FeedbackRepository;
import com.MAHD.smart_learning_assistant_service.repo.QueryHistoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final QueryHistoryRepository queryHistoryRepository;
    private final FeedbackRepository feedbackRepository;

    public ChatService(ChatClient.Builder builder, QueryHistoryRepository queryHistoryRepository, FeedbackRepository feedbackRepository) {
        this.chatClient = builder.build();
        this.queryHistoryRepository = queryHistoryRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public String ask(String userId, String prompt) {
        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        ChatHistory history = new ChatHistory(userId, prompt, response);
        queryHistoryRepository.save(history);

        return response;
    }

    public Flux<String> askWithStream(String userId, String prompt) {
        ChatHistory history = new ChatHistory(userId, prompt, "");
        queryHistoryRepository.save(history);

        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content()
                .doOnNext(chunk -> {
                    history.setResponse(history.getResponse() + chunk);
                    queryHistoryRepository.save(history);
                })
                .publishOn(Schedulers.boundedElastic());
    }

    public List<ChatHistory> getHistory(String userId) {
        return queryHistoryRepository.findByUserId(userId);
    }

    public String submitFeedback(String userId, String feedback) {
        Optional<UserFeedback> existingFeedback = feedbackRepository.findByUserId(userId);

        if (existingFeedback.isPresent()) {
            existingFeedback.get().setFeedback(feedback);
            feedbackRepository.save(existingFeedback.get());
            return "Feedback updated for user: " + userId;
        } else {
            UserFeedback newFeedback = new UserFeedback(userId, feedback);
            feedbackRepository.save(newFeedback);
            return "Feedback submitted for user: " + userId;
        }
    }

    public Optional<UserFeedback> getFeedback(String userId) {
        return feedbackRepository.findByUserId(userId);
    }

    public List<UserFeedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public List<ChatHistory> getAllAsks() {
        return queryHistoryRepository.findAll();
    }
}