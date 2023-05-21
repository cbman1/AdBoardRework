package ru.kpfu.itis.adboardrework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.adboardrework.models.Advert;
import ru.kpfu.itis.adboardrework.models.State;
import ru.kpfu.itis.adboardrework.models.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long> {
    @Query("select max(a.id) from Advert a where a.authorId=:user")
    Long getMaxId(User user);
    List<Advert> findAllByAuthorId(User user);

    Optional<Advert> findByNameAndAuthorId(String name, User user);

    List<Advert> findAllByAuthorIdAndState(User user, State state);
}
