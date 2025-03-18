package com.MAHD.smart_learning_assistant_service.domain.usecases;

import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.domain.repository.FeedbackRepo;

import java.util.List;
import java.util.Optional;

public class UserFeedbackUseCases {

    private final FeedbackRepo feedbackRepo;

    public UserFeedbackUseCases(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }


    public String submitFeedback(String userId, String feedback) {
        return feedbackRepo.submitFeedback(userId, feedback);
    }
    public List<UserFeedback> getAllFeedbacks() {
        return feedbackRepo.getAllFeedbacks();
    }

    public Optional<UserFeedback> getFeedbackByUserId(String userId) {
        return feedbackRepo.getFeedbackByUserId(userId);
    }
}
