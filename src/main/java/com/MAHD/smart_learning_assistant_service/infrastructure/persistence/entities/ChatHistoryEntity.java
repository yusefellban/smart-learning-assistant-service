package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId; // Keeping it as String for UUID compatibility


    @Column(columnDefinition = "TEXT" ,nullable = false)
    private String prompt;

    @Column(columnDefinition = "TEXT" ,nullable = false)
    private String response;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    public ChatHistoryEntity(String userId, String prompt, String response) {
        this.userId = userId;
        this.prompt = prompt;
        this.response = response;
    }

    public ChatHistoryEntity(Long id, String userId, String prompt, String response) {
        this.id = id;
        this.userId = userId;
        this.prompt = prompt;
        this.response = response;
    }
}


