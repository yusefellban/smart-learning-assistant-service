package com.MAHD.smart_learning_assistant_service.application.dto;

import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FeedbackResponse {
    private String userId;
    private String feedback;

    public static FeedbackResponse toFeedbackResponse(UserFeedback userFeedback) {
        return new FeedbackResponse(userFeedback.getUserId(), userFeedback.getFeedback());
    }
}
