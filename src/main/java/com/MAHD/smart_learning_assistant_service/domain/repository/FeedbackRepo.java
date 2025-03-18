package com.MAHD.smart_learning_assistant_service.domain.repository;

import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.UserFeedbackEntity;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepo {
    String submitFeedback(String userId, String feedback);
    Optional<UserFeedback> getFeedbackByUserId(String userId);
     List<UserFeedback> getAllFeedbacks() ;
}
