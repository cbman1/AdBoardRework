package ru.kpfu.itis.adboardrework.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.adboardrework.models.Chat;
import ru.kpfu.itis.adboardrework.models.Message;
import ru.kpfu.itis.adboardrework.repositories.ChatRepository;
import ru.kpfu.itis.adboardrework.repositories.MessageRepository;
import ru.kpfu.itis.adboardrework.repositories.UserRepository;
import ru.kpfu.itis.adboardrework.services.ChatService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final UserRepository usersRepository;
    private final ChatRepository chatsRepository;
    private final MessageRepository messagesRepository;

    @Override
    public Chat getOrCreateChatByUsersIds(Long senderId, Long recipientId, boolean createIfNotExist) {
        Chat chat = chatsRepository.findBySenderIdAndRecipientId(senderId, recipientId).orElse(null);
        if (chat == null) {
            if (createIfNotExist) {
                var sender = usersRepository.findById(senderId).get();
                var recipient = usersRepository.findById(recipientId).get();
                chat = Chat.builder()
                        .senderId(senderId)
                        .recipientId(recipientId)
                        .senderUsername(sender.getFirstName())
                        .senderAvatar(sender.getAvatarPath())
                        .recipientUsername(recipient.getFirstName())
                        .recipientAvatar(recipient.getAvatarPath())
                        .build();
                return chatsRepository.save(chat);
            }
        }
        return chat;
    }

    @Override
    public List<Chat> getAllChatsByUserId(Long userId) {
        return chatsRepository.findAllByUserId(userId);
    }

    @Override
    public Message saveNewMessage(Message message) {
        return messagesRepository.save(message);
    }

    @Override
    public Long countNewMessages(Long senderId, Long recipientId) {
        return messagesRepository.countBySenderIdAndRecipientIdAndState(senderId, recipientId, Message.State.RECEIVED);
    }

    @Override
    public List<Message> findMessages(Long senderId, Long recipientId) {
        var chat = chatsRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        Long id = null;
        if (chat.isPresent()) {
            id = chat.get().getId();
        }
        return messagesRepository.findByChatId(id);
    }
}
