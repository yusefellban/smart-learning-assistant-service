package com.MAHD.smart_learning_assistant_service.domain.mapper;

import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.UserFeedbackEntity;

import java.util.List;
import java.util.stream.Collectors;

public class FeedbackToDomainMapper {
    /**
     * Converts a {@link UserFeedbackEntity} to a {@link UserFeedback} domain object.
     *
     * @param feedbackEntity the entity to convert
     * @return the converted domain object
     */
    public static UserFeedback toDomain(UserFeedbackEntity feedbackEntity) {
        return new UserFeedback(
                feedbackEntity.getId(),
                feedbackEntity.getUserId(),
                feedbackEntity.getFeedback()
        );
    }

    /**
     * Converts a list of {@link UserFeedbackEntity} objects into a list of {@link UserFeedback} domain objects.
     *
     * @param entityList the list of entities to be converted
     * @return a list of domain objects
     */
    public static List<UserFeedback> toDomainList(List<UserFeedbackEntity> entityList) {
        return entityList.stream()
                .map(FeedbackToDomainMapper::toDomain)
                .collect(Collectors.toList());
    }
}
