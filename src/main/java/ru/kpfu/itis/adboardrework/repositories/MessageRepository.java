package ru.kpfu.itis.adboardrework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.adboardrework.models.Message;


import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Long countBySenderIdAndRecipientIdAndState(Long senderId, Long recipientId, Message.State state);

    List<Message> findByChatId(Long chatId);
}
