package com.example.websocket.dto;

public class ChatMessageRequest {

    private String roomId;
    private String writer;
    private String message;

    public ChatMessageRequest() {
    }

    public ChatMessageRequest(String roomId, String writer, String message) {
        this.roomId = roomId;
        this.writer = writer;
        this.message = message;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getWriter() {
        return writer;
    }

    public String getMessage() {
        return message;
    }
}
