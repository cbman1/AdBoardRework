package ru.kpfu.itis.adboardrework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.adboardrework.models.Reviews;
import ru.kpfu.itis.adboardrework.models.User;

import java.util.List;


@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    @Query(value = "select avg(score) from reviews where recipient_id = :userId", nativeQuery = true)
    Float getAverageScoreUser(@Param("userId") Long userId);
    List<Reviews> findByRecipientId(User recipientId);
}
