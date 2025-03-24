package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo;


import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.UserFeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FeedbackRepository extends JpaRepository<UserFeedbackEntity, Long> {
    Optional<UserFeedbackEntity> findByUserId(String userId);


}


