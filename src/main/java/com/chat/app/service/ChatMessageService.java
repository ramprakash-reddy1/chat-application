package com.chat.app.service;

import com.chat.app.dto.ChatMessageDTO;

import java.util.List;

public interface ChatMessageService {

    List<ChatMessageDTO> getAllMessageBySenderIdAndRecipientId(String senderId,String recipientId);
}
