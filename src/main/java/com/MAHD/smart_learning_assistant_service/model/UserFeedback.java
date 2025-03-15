package com.MAHD.smart_learning_assistant_service.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String feedback;

    public UserFeedback(String userId, String feedback) {
        this.userId = userId;
        this.feedback = feedback;
    }
}

