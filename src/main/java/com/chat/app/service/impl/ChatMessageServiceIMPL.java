package com.chat.app.service.impl;

import com.chat.app.dto.ChatMessageDTO;
import com.chat.app.model.ChatMessage;
import com.chat.app.repository.ChatMessageRepository;
import com.chat.app.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ChatMessageServiceIMPL implements ChatMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageServiceIMPL.class);

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageServiceIMPL(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public List<ChatMessageDTO> getAllMessageBySenderIdAndRecipientId(String senderId, String recipientId) {
        List<ChatMessageDTO> chatMessageDTOS=new ArrayList<>();
        for (ChatMessage chatMessage : chatMessageRepository.findChatMessagesBetweenTwoUsers(senderId, recipientId)) {
            ChatMessageDTO chatMessageDTO = new ChatMessageDTO();
            chatMessageDTO.setSenderId(chatMessage.getSenderId());
            chatMessageDTO.setRecipientId(chatMessage.getRecipientId());
            chatMessageDTO.setContent(chatMessage.getContent());
            chatMessageDTO.setFileType(chatMessage.getFileType());
            chatMessageDTO.setFileUrl(chatMessage.getFileUrl());
            chatMessageDTO.setTimeStamp(chatMessage.getTimestamp());
            chatMessageDTOS.add(chatMessageDTO);
        }
        LOGGER.info("Fetched all messages of sendId: {} and recipientId: {}",senderId,recipientId);
        return chatMessageDTOS.stream().sorted(Comparator.comparing(ChatMessageDTO::getTimeStamp)).toList();
    }
}
