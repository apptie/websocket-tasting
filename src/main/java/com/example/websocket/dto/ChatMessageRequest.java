package com.example.websocket.dto;

public class ChatMessageRequest {

    private String targetUserName;
    private String writer;
    private String message;

    public ChatMessageRequest() {
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public String getWriter() {
        return writer;
    }

    public String getMessage() {
        return message;
    }
}
