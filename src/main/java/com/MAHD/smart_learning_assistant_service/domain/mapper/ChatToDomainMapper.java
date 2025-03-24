package com.MAHD.smart_learning_assistant_service.domain.mapper;

import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.ChatHistoryEntity;

import java.util.List;

public class ChatToDomainMapper {

    /**
     * Converts a list of {@link ChatHistoryEntity} objects into a list of {@link ChatHistory} domain objects.
     * <p>
     * {@code Why?} This removes manual iteration, making the code more readable and concise.
     * Instead of using explicit loops, Java Streams provide a more functional and declarative approach.
     * </p>
     *
     * <h3>Previous Implementation:</h3>
     * <hr>
     * <blockquote><pre>
     * private List<ChatHistory> toDomainList(List<ChatHistoryEntity> chatHistoryEntity) {
     *     List<ChatHistory> result = new ArrayList<>();
     *     for (ChatHistoryEntity e : chatHistoryEntity) {
     *         result.add(new ChatHistory(e.getId(), e.getUserId(),
     *                 e.getPrompt(), e.getResponse()));
     *     }
     *     return result;
     * }
     * </pre></blockquote>
     * <hr>
     *
     * <h3>Improved Implementation:</h3>
     * <p>
     * Uses Java Streams to map each entity to its corresponding domain model, eliminating explicit iteration.
     * </p>
     *
     * @param entities the list of {@link ChatHistoryEntity} objects to be converted
     * @return a list of {@link ChatHistory} domain objects
     */
    public static List<ChatHistory> toDomainList(List<ChatHistoryEntity> entities) {
        return entities.stream()
                .map(ChatToDomainMapper::toDomain)
                .toList();
    }

    private static ChatHistory toDomain(ChatHistoryEntity chatHistoryEntity) {
        return new ChatHistory(chatHistoryEntity.getId(), chatHistoryEntity.getUserId(),
                chatHistoryEntity.getPrompt(), chatHistoryEntity.getResponse(), chatHistoryEntity.getTimestamp());
    }

}
