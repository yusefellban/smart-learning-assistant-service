package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities;


import com.MAHD.smart_learning_assistant_service.domain.mapper.FeedbackToDomainMapper;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "user_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFeedbackEntity extends FeedbackToDomainMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String feedback;

    public UserFeedbackEntity(String userId, String feedback) {
        this.userId = userId;
        this.feedback = feedback;
    }


}

