package com.example.websocket.controller;

import com.example.websocket.dto.ChatMessageRequest;
import com.example.websocket.dto.ChatMessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketStompChatController {

    @MessageMapping(value = "/chat/enter")
    @SendTo("/topic/static")
    public ChatMessageResponse enter(ChatMessageRequest request) {
        final String message = request.getWriter() + " 님이 채팅방에 참여하였습니다.";

        return new ChatMessageResponse(request.getWriter(), message);
    }

    @MessageMapping(value = "/chat/message")
    @SendTo("/topic/static")
    public ChatMessageResponse message(ChatMessageRequest request) {
        return new ChatMessageResponse(request.getWriter(), request.getMessage());
    }
}
