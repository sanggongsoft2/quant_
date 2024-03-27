package com.quant_socket.handlers;

import com.quant_socket.services.EquitiesSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class EquitiesSnapshotHandler extends TextWebSocketHandler {

    private final EquitiesSnapshotService service;

    @Override
    public void handleMessage(WebSocketSession ws, WebSocketMessage<?> message) throws Exception {
        final String payload = (String) message.getPayload();
        log.info("Received Message: {}", payload);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
        service.addSession(ws);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        service.removeSession(ws);
    }
}
