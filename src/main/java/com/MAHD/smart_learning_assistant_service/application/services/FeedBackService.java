package com.MAHD.smart_learning_assistant_service.application.services;

import com.MAHD.smart_learning_assistant_service.application.dto.FeedbackResponse;
import com.MAHD.smart_learning_assistant_service.domain.repository.FeedbackRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Feedback service is the main service class that acts as an intermediary between the controllers
 * and the repository layer for handling feedback submissions.
 * <p>
 * This service interacts with:
 * <ul>
 *
 *     <li>{@link FeedbackRepo} - For submitting and retrieving user feedback.</li>
 * </ul>
 * </p>
 *
 * <h3>Design Considerations:</h3>
 * <ul>
 *     <li>Encapsulates business logic for AI feedback handling.</li>
 *     <li>Ensures separation of concerns by keeping repository access within a dedicated service layer.</li>
 * </ul>
 */
@Service
public class FeedBackService {

    FeedbackRepo feedbackRepo;

    public FeedBackService(FeedbackRepo feedbackRepo) {
        this.feedbackRepo = feedbackRepo;
    }

    public ResponseEntity<String> submitFeedback(String userId, String feedback) {
        return ResponseEntity.status(HttpStatus.OK).body(feedbackRepo.submitFeedback(userId, feedback));
    }

    public ResponseEntity<FeedbackResponse> getFeedback(String userId) {
        return feedbackRepo.getFeedbackByUserId(userId)
                .map(FeedbackResponse::toFeedbackResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());

    }

    public List<FeedbackResponse> getAllFeedbacks() {
        return feedbackRepo.getAllFeedbacks()
                .stream()
                .map(FeedbackResponse::toFeedbackResponse)
                .collect(Collectors.toList());
    }


}
