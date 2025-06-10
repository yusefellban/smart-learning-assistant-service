package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repositoryImp;

import com.MAHD.smart_learning_assistant_service.domain.mapper.FeedbackToDomainMapper;
import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.domain.repository.FeedbackRepo;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.UserFeedbackEntity;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo.FeedbackRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link FeedbackRepo} interface for managing user feedback persistence.
 * <p>
 * This class serves as an adapter between the domain layer and the persistence layer, handling the conversion
 * between {@link UserFeedbackEntity} (database entity) and {@link UserFeedback} (domain model).
 * It ensures proper feedback storage, retrieval, and updating in the database.
 * </p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *     <li>Stores and updates user feedback efficiently.</li>
 *     <li>Retrieves feedback data in domain-friendly format.</li>
 *     <li>Uses Java Streams for improved readability and maintainability.</li>
 * </ul>
 *
 * <p>
 * {@code Why?} This implementation ensures loose coupling between layers, improving maintainability and testability.
 * </p>
 *
 * @author Yousef El-llban
 * @version 1.0
 * @see FeedbackRepo
 * @see UserFeedbackEntity
 * @see UserFeedback
 */
@Repository
public class FeedbackRepoImpl implements FeedbackRepo {
    private final FeedbackRepository feedbackRepository;

    /**
     * Constructs a new {@code FeedbackRepoImpl} with the given repository.
     *
     * @param feedbackRepository the repository for accessing feedback data
     */
    public FeedbackRepoImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Submits or updates user feedback based on user ID.
     *
     * @param userId   the ID of the user
     * @param feedback the feedback content
     * @return a message indicating whether the feedback was submitted or updated
     */
    @Override
    public String submitFeedback(String userId, String feedback) {
        UserFeedbackEntity feedbackEntity = feedbackRepository.findByUserId(userId)
                .map(existingFeedback -> {
                    existingFeedback.setFeedback(feedback);
                    return existingFeedback;
                })
                .orElse(new UserFeedbackEntity(userId, feedback));

        feedbackRepository.save(feedbackEntity);
        return feedbackEntity.getId() == null ?
                "New feedback submitted for user: " + userId :
                "Feedback updated for user: " + userId;
    }

    /**
     * Retrieves user feedback based on user ID.
     *
     * @param userId the ID of the user
     * @return an optional containing the user's feedback if found
     */
    @Override
    public Optional<UserFeedback> getFeedbackByUserId(String userId) {
//        return feedbackRepository.findByUserId(userId).map(FeedbackToDomainMapper::toDomain);
        return feedbackRepository.findByUserId(userId).map(FeedbackToDomainMapper::toDomain);
    }

    /**
     * Retrieves all stored feedback records.
     *
     * @return a list of all user feedback entries
     */
    @Override
    public List<UserFeedback> getAllFeedbacks() {
        return FeedbackToDomainMapper.toDomainList(feedbackRepository.findAll());
    }


}
