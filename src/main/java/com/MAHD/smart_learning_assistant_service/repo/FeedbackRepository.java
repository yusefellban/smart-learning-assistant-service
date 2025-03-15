package com.MAHD.smart_learning_assistant_service.repo;


import com.MAHD.smart_learning_assistant_service.model.UserFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<UserFeedback, Long> {
    Optional<UserFeedback> findByUserId(String userId);
}


