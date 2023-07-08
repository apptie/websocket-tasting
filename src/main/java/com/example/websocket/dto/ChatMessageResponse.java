package com.example.websocket.dto;

public class ChatMessageResponse {

    private String targetUserName;
    private String writer;
    private String message;

    public ChatMessageResponse(String targetUserName, String writer, String message) {
        this.targetUserName = targetUserName;
        this.writer = writer;
        this.message = message;
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
