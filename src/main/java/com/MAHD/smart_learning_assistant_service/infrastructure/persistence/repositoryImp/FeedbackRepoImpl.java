package com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repositoryImp;

import com.MAHD.smart_learning_assistant_service.domain.model.UserFeedback;
import com.MAHD.smart_learning_assistant_service.domain.repository.FeedbackRepo;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.entities.UserFeedbackEntity;
import com.MAHD.smart_learning_assistant_service.infrastructure.persistence.repo.FeedbackRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class FeedbackRepoImpl implements FeedbackRepo {
    private final FeedbackRepository feedbackRepository;

    public FeedbackRepoImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public String submitFeedback(String userId, String feedback) {
        Optional<UserFeedbackEntity> existingFeedback = feedbackRepository.findByUserId(userId);

        if (existingFeedback.isPresent()) {
            existingFeedback.get().setFeedback(feedback);
             feedbackRepository.save(existingFeedback.get());
            return "Feedback updated for user: " + userId;
        } else {
            UserFeedbackEntity newFeedback = new UserFeedbackEntity(userId, feedback);
            feedbackRepository.save(newFeedback);
            return "Feedback submitted for user: " + userId;
        }
    }

    private Optional<UserFeedback> toDomainOptional(Optional<UserFeedbackEntity> e) {
        return e.map(feedback -> new UserFeedback(
                feedback.getId(),
                feedback.getUserId(),
                feedback.getFeedback()
        ));
    }


    private List<UserFeedback> toDomainList(List<UserFeedbackEntity> userFeedbackEntityList){
        List<UserFeedback> feedbacks = new ArrayList<>();
        for (UserFeedbackEntity e : userFeedbackEntityList) {
            feedbacks.add(new UserFeedback(e.getId(),e.getUserId(),e.getFeedback()));
        }
        return feedbacks;
    }

    @Override
    public Optional<UserFeedback> getFeedbackByUserId(String userId) {
        return toDomainOptional(feedbackRepository.findByUserId(userId));


    }

    @Override
    public List<UserFeedback> getAllFeedbacks() {
        return toDomainList(feedbackRepository.findAll());
    }
}
