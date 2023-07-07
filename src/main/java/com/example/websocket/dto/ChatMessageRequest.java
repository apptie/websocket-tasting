package com.example.websocket.dto;

public class ChatMessageRequest {

    private String writer;
    private String message;

    public ChatMessageRequest() {
    }

    public String getWriter() {
        return writer;
    }

    public String getMessage() {
        return message;
    }
}
