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

    @MessageMapping("/chat/enter")
    public void enter(ChatMessageRequest request) {
        final String message = request.getWriter() + " 님이 채팅방에 참여하였습니다.";
        final ChatMessageResponse response = new ChatMessageResponse(request.getWriter(), message);

        template.convertAndSend("/topic/" + request.getRoomId(), response);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageRequest request) {
        final ChatMessageResponse response = new ChatMessageResponse(request.getWriter(),
                request.getMessage());

        template.convertAndSend("/topic/" + request.getRoomId(), response);
    }
}
