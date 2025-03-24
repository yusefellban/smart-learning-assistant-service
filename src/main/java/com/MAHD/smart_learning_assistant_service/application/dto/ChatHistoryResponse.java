package com.MAHD.smart_learning_assistant_service.application.dto;

import com.MAHD.smart_learning_assistant_service.domain.model.ChatHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class ChatHistoryResponse {
    private String userId; // Keeping it as String for UUID compatibility
    private String prompt;
    private String response;
    private LocalDateTime timestamp ;
    public static ChatHistoryResponse toChatHistoryResponse(ChatHistory chatHistory) {
        return new ChatHistoryResponse(chatHistory.getUserId(), chatHistory.getPrompt(), chatHistory.getResponse(), chatHistory.getTimestamp());
    }
}
