package com.MAHD.smart_learning_assistant_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatHistory {

    private Long id;
    private String userId; // Keeping it as String for UUID compatibility
    private String prompt;
    private String response;
    private LocalDateTime timestamp ;

}


