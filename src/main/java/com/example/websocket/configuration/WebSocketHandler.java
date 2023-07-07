package com.example.websocket.configuration;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> clientSessions = new ArrayList<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        log.info("메세지 페이로드 - {}", payload);

        for (WebSocketSession webSocketSession : clientSessions) {
            webSocketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("클라이언트 접속 - {}", session);

        clientSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("클라이언트 접속 해제 - {}", session);
        log.info("클라이언트 접속 해제 사유 - {}", status);

        clientSessions.remove(session);
    }
}
