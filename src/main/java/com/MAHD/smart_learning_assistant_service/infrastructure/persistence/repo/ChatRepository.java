package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo;

import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.ChatHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatHistoryEntity, Long> {
    List<ChatHistoryEntity> findByUserId(String userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ChatHistoryEntity c WHERE c.timestamp < :expiryTime")
    int deleteOldChats(LocalDateTime expiryTime);
}