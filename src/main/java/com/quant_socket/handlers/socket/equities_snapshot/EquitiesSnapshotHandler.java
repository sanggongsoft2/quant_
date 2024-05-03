package com.quant_socket.handlers.socket.equities_snapshot;

import com.quant_socket.services.EquitiesSnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class EquitiesSnapshotHandler extends TextWebSocketHandler {

    private final EquitiesSnapshotService service;
    @Override
    public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
        service.afterConnectionEstablished(ws);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        service.afterConnectionClosed(ws, status);
    }
}
