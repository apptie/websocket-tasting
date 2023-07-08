package com.example.websocket;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.websocket.dto.ChatMessageRequest;
import com.example.websocket.dto.ChatMessageResponse;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class StompTest {

    @LocalServerPort
    Integer port;

    BlockingQueue<ChatMessageResponse> responses;
    WebSocketStompClient stompClient;

    @BeforeEach
    void setUp() {
        responses = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    void stompTest() throws Exception {
        final ChatMessageRequest request = new ChatMessageRequest("room1", "user1", "안녕하세요");
        final WebSocketHttpHeaders webSocketHttpHeaders = new WebSocketHttpHeaders();
        final StompHeaders stompHeaders = new StompHeaders();
        final String topicPath = "/topic/" + request.getRoomId();
        final CompletableFuture<StompSession> connect = stompClient
                .connectAsync(
                        "ws://localhost:" + port + "/stomp/chat",
                        webSocketHttpHeaders,
                        stompHeaders,
                        new CustomStompSessionHandlerAdapter<>(topicPath, ChatMessageResponse.class, responses));
        final StompSession stompSession = connect.get(60, TimeUnit.SECONDS);

        stompSession.send(topicPath, request);

        final ChatMessageResponse actual = responses.poll(60, TimeUnit.SECONDS);

        assertThat(actual.getMessage()).isEqualTo(request.getMessage());
    }
}
