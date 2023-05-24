package ru.kpfu.itis.adboardrework.controllers.mvc;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.adboardrework.models.Chat;
import ru.kpfu.itis.adboardrework.models.Message;
import ru.kpfu.itis.adboardrework.models.User;
import ru.kpfu.itis.adboardrework.services.ChatService;
import ru.kpfu.itis.adboardrework.services.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatsController {
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @GetMapping
    public String getChatsPage(Principal principal, Model model) {

        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }

        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("chats", chatService.getAllChatsByUserId(user.getId()));
        return "chat_page";
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        Chat chat = chatService.getOrCreateChatByUsersIds(message.getSenderId(),
                message.getRecipientId(),
                true);
        message.setChat(chat);
        message = chatService.saveNewMessage(message);

        messagingTemplate.convertAndSendToUser(message.getRecipientId().toString(), "/queue/messages",
                Message.builder()
                        .id(message.getId())
                        .senderId(message.getSenderId())
                        .senderUsername(message.getSenderUsername())
                        .recipientId(message.getRecipientId())
                        .recipientUsername(message.getRecipientUsername())
                        .content(message.getContent())
                        .sendDate(message.getSendDate())
                        .state(message.getState())
                        .build()
        );
    }

    @GetMapping("/get/chat/{sender-id}/{recipient-id}")
    public String getUserProfile(Principal principal,
                                 @PathVariable("sender-id") Long senderId,
                                 @PathVariable("recipient-id") Long recipientId,
                                 Model model) {
        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }

        List<Message> messages = chatService.findMessages(senderId, recipientId);
        Long otherUserId = senderId.equals(userService.getUserByEmail(principal.getName()).getId()) ? recipientId : senderId;
        model.addAttribute("currentUser", userService.getUserByEmail(principal.getName()));
        model.addAttribute("otherUserId", otherUserId);
        model.addAttribute("otherUser", userService.getUserById(otherUserId));
        model.addAttribute("messages", messages);
        return "chat_partial";
    }

    @GetMapping("/get/chat/{username}")
    public String getChat(Principal principal,
                          @PathVariable("username") String username, Model model) {
        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }
        User user = userService.getUserByEmail(username);
        Long userId;
        if (user != null) {
            userId = user.getId();
            chatService.getOrCreateChatByUsersIds(userService.getUserByEmail(principal.getName()).getId(), userId, true);
            var messages = chatService.findMessages(userService.getUserByEmail(principal.getName()).getId(), userId);
            model.addAttribute("currentUser", userService.getUserByEmail(principal.getName()));
            model.addAttribute("otherUserId", userId);
            model.addAttribute("otherUser", user);
            model.addAttribute("messages", messages);
        } else {
            model.addAttribute("nullable", null);
        }
        return "chat_partial";
    }


    @GetMapping("/new/{id}")
    public String getNewChat(Principal principal,
                             @PathVariable Long id,
                             Model model) {
        if (principal == null) {
            model.addAttribute("errorMessage", "You must be logged in");
            return "error";
        }
        Chat chat = chatService.getOrCreateChatByUsersIds(userService.getUserByEmail(principal.getName()).getId(), id, true);
        User user = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("chats", chatService.getAllChatsByUserId(user.getId()));
        return "redirect:/chats";
    }


}
