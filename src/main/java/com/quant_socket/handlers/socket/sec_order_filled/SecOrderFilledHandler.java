package com.quant_socket.handlers.socket.sec_order_filled;

import com.quant_socket.services.SecuritiesOrderFilledService;
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
public class SecOrderFilledHandler extends TextWebSocketHandler {

    private final SecuritiesOrderFilledService service;

    @Override
    public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
        service.addSession(ws);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        service.removeSession(ws);
    }
}
