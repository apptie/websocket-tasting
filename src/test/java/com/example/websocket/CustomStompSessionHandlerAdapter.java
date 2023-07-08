package com.example.websocket;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class CustomStompSessionHandlerAdapter<T> extends StompSessionHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String topicPath;
    private final Class<T> responseType;
    private final BlockingQueue<T> responses;

    public CustomStompSessionHandlerAdapter(
            String topicPath,
            Class<T> responseType,
            BlockingQueue<T> responses
    ) {
        this.topicPath = topicPath;
        this.responseType = responseType;
        this.responses = responses;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(topicPath, this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.info("error : ", exception);
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        super.handleTransportError(session, exception);
        logger.warn("transport error : ", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return responseType;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("payload : {}", payload);
        responses.offer(responseType.cast(payload));
    }
}
