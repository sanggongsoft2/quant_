package com.quant_socket.handlers.socket.equities_index_indicator;

import com.quant_socket.services.EquitiesBatchDataService;
import com.quant_socket.services.EquityIndexIndicatorService;
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
public class EquitiesIndexIndicatorHandler extends TextWebSocketHandler {

    private final EquityIndexIndicatorService service;

    @Override
    public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
        service.addSession(ws);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        service.removeSession(ws);
    }
}
