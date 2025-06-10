package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repositoryImp;


import com.MAHD.smart_learning_assistant_service.application.exceptions.AgentNotRunningException;
import com.MAHD.smart_learning_assistant_service.application.exceptions.UserNotFoundException;
import com.MAHD.smart_learning_assistant_service.domain.mapper.ChatToDomainMapper;
import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.ChatHistoryEntity;
import com.MAHD.smart_learning_assistant_service.domain.repository.AiAssistantRepo;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo.ChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link AiAssistantRepo} that provides database persistence and real-time
 * AI-driven chat interactions for the Smart Learning Assistant Service.
 *
 * <p>
 * This repository handles chat history management and interaction with an AI assistant using
 * a reactive, non-blocking approach via {@link Flux} and {@link Mono}.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Handles user prompts and generates AI responses.</li>
 *     <li>Streams responses in real-time for a seamless user experience.</li>
 *     <li>Persists chat history asynchronously to optimize performance.</li>
 *     <li>Utilizes {@code ConcurrentHashMap} for efficient response buffering.</li>
 * </ul>
 */
@Repository
public class AiAssistantRepoImpl implements AiAssistantRepo {

    private final ChatRepository chatRepository;

    private final ChatClient chatClient;

    private static final Logger logger = LoggerFactory.getLogger(AiAssistantRepoImpl.class);
    private final Map<String, StringBuilder> userResponseBuffers = new ConcurrentHashMap<>();


    public AiAssistantRepoImpl(ChatRepository chatRepository, ChatClient.Builder builder) {
        this.chatRepository = chatRepository;
        this.chatClient = builder.build();
    }


    /**
     * Processes a user prompt synchronously and stores the response in the database.
     *
     * @param userId the ID of the user submitting the prompt
     * @param prompt the user input prompt
     * @return the AI-generated response
     */
    @Override
    public Optional<String> userPrompt(String userId, String prompt) {
        try {
            String response = chatClient.prompt().user(prompt).call().content();
            chatRepository.save(createChatHistoryEntity(userId, prompt, response));
            assert response != null;
            return Optional.of(response);
        } catch (ResourceAccessException e) {
            throw new AgentNotRunningException("Agent not running!! :)");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Processes a user's prompt as a reactive stream, generating responses incrementally
     * and updating the chat history asynchronously.
     * <p>
     * {@code Why?} This approach ensures efficient, real-time streaming of responses while
     * maintaining a non-blocking architecture. Instead of handling requests synchronously,
     * it leverages Project Reactor's reactive streams for scalability.
     * </p>
     *
     * <h3>Previous Implementation:</h3>
     * <hr>
     * <blockquote><pre>
     * public String userPrompt(String userId, String prompt) {
     *     ChatHistoryEntity chatHistory = createChatHistoryEntity(userId, prompt, "");
     *     chatRepository.save(chatHistory);
     *
     *     String response = chatClient.prompt()
     *                                 .user(prompt)
     *                                 .execute()
     *                                 .getContent();
     *
     *     chatHistory.setResponse(response);
     *     chatRepository.save(chatHistory);
     *
     *     return response;
     * }
     * </pre></blockquote>
     * <hr>
     *
     * <h3>Improved Implementation:</h3>
     * <p>
     * This version enables real-time response streaming while maintaining data persistence.
     * It utilizes:
     * <ul>
     *     <li>Asynchronous database interactions via {@link Mono}.</li>
     *     <li>Non-blocking response accumulation using {@link Flux}.</li>
     *     <li>Efficient thread management via {@code Schedulers.boundedElastic()}.</li>
     * </ul>
     * </p>
     *
     * @param userId the ID of the user requesting a response
     * @param prompt the input prompt provided by the user
     * @return a {@link Flux} stream emitting response chunks in real-time
     */
    @Override
    public Flux<String> userPromptStream(String userId, String prompt) {
        userResponseBuffers.putIfAbsent(userId, new StringBuilder()); // Ensure unique entries
        return Mono.fromCallable(() -> chatRepository.save(createChatHistoryEntity(userId, prompt, "")))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(saved -> logger.info("Chat history initialized for user: {}", userId))
                .onErrorResume(e -> {
                    logger.error("Error initializing chat history for user {}: {}", userId, e.getMessage(), e);
                    return Mono.error(new RuntimeException("!!!Error initializing chat history for user " + userId, e));
                })
                .thenMany(
                        chatClient.prompt()
                                .user(prompt)
                                .stream()
                                .content()
                                .doOnNext(chunk -> {
                                    try {
                                        userResponseBuffers.get(userId).append(chunk);
                                        logger.debug("Received chunk for {}: {}", userId, chunk);
                                    } catch (Exception e) {
                                        logger.error("Error processing chunk for user {}: {}", userId, e.getMessage(), e);
                                        throw new RuntimeException("@Error processing chunk for user " + userId, e);
                                    }
                                })
                                .publishOn(Schedulers.boundedElastic())
                                .doOnComplete(() -> {
                                    try {
                                        ChatHistoryEntity history = chatRepository.findByUserId(userId)
                                                .stream()
                                                .findFirst()
                                                .orElse(null);

                                        if (history != null) {
                                            history.setResponse(userResponseBuffers.remove(userId).toString());
                                            chatRepository.save(history);
                                            logger.info("Chat history updated for user: {}", userId);
                                        } else {
                                            logger.warn("Could not find history for user: {}", userId);
                                        }
                                    } catch (Exception e) {
                                        logger.error("Error updating chat history for user {}: {}", userId, e.getMessage(), e);
                                    }
                                })
                                .onErrorResume(e -> {
                                    logger.error("Error during streaming for user {}: {}", userId, e.getMessage(), e);
                                    userResponseBuffers.remove(userId); // Ensure buffer is cleared
                                    return Mono.error(new AgentNotRunningException("Agent not running!!! :) "));
                                })

                );


    }

    /**
     * Retrieves a user's chat history.
     *
     * @param userId the ID of the user
     * @return a list of chat history entries
     */
    @Override
    public Optional<List<ChatHistory>> getUserHistory(String userId) {
        return Optional.ofNullable(chatRepository.findByUserId(userId))
                .map(ChatToDomainMapper::toDomainList)
                .filter(historyList -> !historyList.isEmpty())
                .or(() -> {
                    throw new UserNotFoundException("!User not found: " + userId);
                });
    }


    @Override
    public int deleteOldChats(LocalDateTime expiryTime) {
        return chatRepository.deleteOldChats(expiryTime);
    }


    /// {@code Avoid Repetitive Object Creation }
    private ChatHistoryEntity createChatHistoryEntity(String userId, String prompt, String response) {
        return new ChatHistoryEntity(userId, prompt, response);
    }
}
