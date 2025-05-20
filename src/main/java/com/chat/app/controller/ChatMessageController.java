package com.chat.app.controller;

import com.chat.app.dto.ApiResponse;
import com.chat.app.dto.ChatMessageDTO;
import com.chat.app.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatMessageDTO>>> getAllMessageBySenderIdAndRecipientId(@RequestParam String senderId, @RequestParam String recipientId){
        List<ChatMessageDTO> allMessageBySenderIdAndRecipientId = chatMessageService.getAllMessageBySenderIdAndRecipientId(senderId, recipientId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Fetched all messages between the users", allMessageBySenderIdAndRecipientId));
    }

    private final String uploadDir = "uploads"; // relative to the project root or /resources/static/uploads

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        // Ensure directory exists
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // Save file
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filepath = Paths.get(uploadDir, filename);
        Files.write(filepath, file.getBytes());

        // Return local URL for access
        String fileUrl = "http://localhost:8080/uploads/" + filename;
        return ResponseEntity.ok(fileUrl);
    }
        }
