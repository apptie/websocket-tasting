package com.example.websocket.controller;

import com.example.websocket.dto.ChatMessageRequest;
import com.example.websocket.dto.ChatMessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketStompChatController {

    private final SimpMessagingTemplate template;

    public WebSocketStompChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/message")
    public void send(ChatMessageRequest request) {
        final ChatMessageResponse response = new ChatMessageResponse(request.getTargetUserName(), request.getWriter(),
                request.getMessage());

        template.convertAndSendToUser(request.getWriter(), "/queue/message", response);
        template.convertAndSendToUser(request.getTargetUserName(), "/queue/message", response);
    }
}
