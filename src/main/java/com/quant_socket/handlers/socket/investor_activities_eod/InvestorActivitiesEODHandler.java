package com.quant_socket.handlers.socket.investor_activities_eod;

import com.quant_socket.services.InvestActivitiesEODService;
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
public class InvestorActivitiesEODHandler extends TextWebSocketHandler {

    private final InvestActivitiesEODService service;
    @Override
    public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
        service.afterConnectionEstablished(ws);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        service.afterConnectionClosed(ws, status);
    }
}
