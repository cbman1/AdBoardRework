package ru.kpfu.itis.adboardrework.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.adboardrework.dto.UserDto;
import ru.kpfu.itis.adboardrework.models.Advert;
import ru.kpfu.itis.adboardrework.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByHashForConfirm(String hashForConfirm);
    boolean existsUserByEmail(String email);
    @Query("SELECT u.favorites FROM users u WHERE u.email = :email")
    List<Advert> findFavoritesByUserId(@Param("email") String email);
}
