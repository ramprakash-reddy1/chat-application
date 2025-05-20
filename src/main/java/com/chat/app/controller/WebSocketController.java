package com.chat.app.controller;

import com.chat.app.dto.ChatMessageDTO;
import com.chat.app.model.ChatMessage;
import com.chat.app.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChatMessageRepository chatMessageRepository;


    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate, ChatMessageRepository chatMessageRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMessageRepository = chatMessageRepository;
    }



    @MessageMapping("/chat")
    public void sendMessage(@Payload ChatMessageDTO chatMessageDTO){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSenderId(chatMessageDTO.getSenderId());
        chatMessage.setRecipientId(chatMessageDTO.getRecipientId());
        chatMessage.setContent(chatMessageDTO.getContent());
        if(chatMessageDTO.getFileUrl()!=null && chatMessageDTO.getFileType()!=null) {
            chatMessage.setFileUrl(chatMessageDTO.getFileUrl());
            chatMessage.setFileType(chatMessageDTO.getFileType());
        }
        chatMessageRepository.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                chatMessageDTO.getRecipientId(),
                        "/queue/messages",
                chatMessageDTO
                );
            }

}
