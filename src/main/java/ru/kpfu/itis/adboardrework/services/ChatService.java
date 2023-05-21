package ru.kpfu.itis.adboardrework.services;

import ru.kpfu.itis.adboardrework.models.Chat;
import ru.kpfu.itis.adboardrework.models.Message;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Chat getOrCreateChatByUsersIds(Long senderId, Long recipientId, boolean createIfNotExist);
    List<Chat> getAllChatsByUserId(Long userId);
    Message saveNewMessage(Message message);
    Long countNewMessages(Long senderId, Long recipientId);
    List<Message> findMessages(Long senderId, Long recipientId);
}
