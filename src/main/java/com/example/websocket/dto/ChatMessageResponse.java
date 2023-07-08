package com.example.websocket.dto;

public class ChatMessageResponse {

    private String writer;
    private String message;

    public ChatMessageResponse() {
    }

    public ChatMessageResponse(String writer, String message) {
        this.writer = writer;
        this.message = message;
    }

    public String getWriter() {
        return writer;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ChatMessageResponse{" +
                "writer='" + writer + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
