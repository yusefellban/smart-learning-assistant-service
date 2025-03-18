package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repositoryImp;


import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.ChatHistoryEntity;
import com.MAHD.smart_learning_assistant_service.domain.repository.AiAssistantRepo;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo.ChatRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;


@Repository
public class AiAssistantRepoImpl implements AiAssistantRepo {

    private final ChatRepository chatRepository;

    private final ChatClient chatClient;

    public AiAssistantRepoImpl(ChatRepository chatRepository, ChatClient.Builder builder) {
        this.chatRepository = chatRepository;
        this.chatClient = builder.build();
    }

    private ChatHistory toDomain(ChatHistoryEntity chatHistoryEntity) {
        return new ChatHistory(chatHistoryEntity.getId(), chatHistoryEntity.getUserId(),
                chatHistoryEntity.getPrompt(), chatHistoryEntity.getResponse());
    }

    private ChatHistoryEntity toEntity(ChatHistory chatHistory) {
        return new ChatHistoryEntity(chatHistory.getId(), chatHistory.getUserId()
                , chatHistory.getPrompt(), chatHistory.getResponse());
    }

    @Override
    public String userPrompt(String userId, String prompt) {

        String response = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        ChatHistoryEntity history = new ChatHistoryEntity(userId, prompt, response);
        chatRepository.save(history);

        return response;
    }

    @Override
    public Flux<String> userPromptStream(String userId, String prompt) {
        ChatHistoryEntity history = new ChatHistoryEntity(userId, prompt, " ");
        chatRepository.save(history);

        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content()
                .doOnNext(chunk -> {
                    history.setResponse(history.getResponse() + chunk);
                    chatRepository.save(history);
                })
                .publishOn(Schedulers.boundedElastic());
    }

    @Override
    public List<ChatHistory> getUserHistory(String userId) {
        return toDomainList(chatRepository.findByUserId(userId));
    }

    private List<ChatHistory> toDomainList(List<ChatHistoryEntity> chatHistoryEntity) {
        List<ChatHistory> result = new ArrayList<>();
        for (ChatHistoryEntity e : chatHistoryEntity) {
            result.add(new ChatHistory(e.getId(), e.getUserId(),
                    e.getPrompt(), e.getResponse()));
        }
        return result;
    }
}
