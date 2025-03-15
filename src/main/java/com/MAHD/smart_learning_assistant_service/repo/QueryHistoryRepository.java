package com.MAHD.smart_learning_assistant_service.repo;

import com.MAHD.smart_learning_assistant_service.model.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByUserId(String userId);
}
