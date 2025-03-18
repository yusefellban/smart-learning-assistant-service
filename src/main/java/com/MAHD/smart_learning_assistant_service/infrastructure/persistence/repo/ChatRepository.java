package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo;

import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.ChatHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatHistoryEntity, Long> {
    List<ChatHistoryEntity> findByUserId(String userId);
}