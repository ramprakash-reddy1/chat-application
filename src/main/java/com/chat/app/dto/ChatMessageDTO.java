package com.chat.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {

    private String senderId;

    private String recipientId;

    private String content;

    private String chatMessageId;

    private String fileUrl;

    private String fileType;

    private LocalDateTime timeStamp;

}
