package ru.kpfu.itis.adboardrework.services;

import ru.kpfu.itis.adboardrework.dto.NewReviewDto;
import ru.kpfu.itis.adboardrework.models.Reviews;

import java.security.Principal;
import java.util.List;

public interface ReviewsService {
    void addReview(NewReviewDto reviewDto, Long idRecipient, Principal principal);
    List<Reviews> getReviewsUser(Long idRecipient);
    Float getAverageScore(Long idRecipient);
}
