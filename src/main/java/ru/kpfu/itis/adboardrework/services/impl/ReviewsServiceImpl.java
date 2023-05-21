package ru.kpfu.itis.adboardrework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.adboardrework.dto.NewReviewDto;
import ru.kpfu.itis.adboardrework.models.Advert;
import ru.kpfu.itis.adboardrework.models.Reviews;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.repositories.AdvertRepository;
import ru.kpfu.itis.adboardrework.repositories.ReviewsRepository;
import ru.kpfu.itis.adboardrework.repositories.UserRepository;
import ru.kpfu.itis.adboardrework.services.ReviewsService;
import ru.kpfu.itis.adboardrework.services.UserService;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final UserRepository userRepository;
    private final AdvertRepository advertRepository;

    @Override
    public void addReview(NewReviewDto reviewDto, Long idRecipient, Principal principal) {

        //TODO: норм ошибки
        User recipient = userRepository.findById(idRecipient).orElseThrow();
        User thisUser = userRepository.findByEmail(principal.getName()).orElseThrow();
        Advert advertReview = advertRepository.findByNameAndAuthorId(reviewDto.getAdvertName(), recipient).orElseThrow();

        Reviews reviews = reviewsRepository.save(
                Reviews.builder()
                        .text(reviewDto.getText())
                        .score(reviewDto.getScore())
                        .authorId(thisUser)
                        .recipientId(recipient)
                        .advertId(advertReview)
                        .build()
        );
    }

    @Override
    public List<Reviews> getReviewsUser(Long idRecipient) {
        return reviewsRepository.findByRecipientId(userRepository.findById(idRecipient).orElseThrow());
    }

    @Override
    public Float getAverageScore(Long idRecipient) {
        return reviewsRepository.getAverageScoreUser(idRecipient);
    }

}
