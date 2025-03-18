package com.MAHD.smart_learning_assistant_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFeedback {

    private Long id;
    private String userId;
    private String feedback;

    public UserFeedback(String userId, String feedback) {
        this.userId = userId;
        this.feedback = feedback;
    }
}

