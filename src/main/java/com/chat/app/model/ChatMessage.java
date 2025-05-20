package com.chat.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ChatMessageId;

    private String senderId;

    private String recipientId;

    private String content;

    private String fileUrl;

    private String fileType;

    private LocalDateTime timestamp=LocalDateTime.now();
}
