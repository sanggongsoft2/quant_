package com.quant_socket.handlers.socket.seq_quote;

import com.quant_socket.services.SeqQuoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
@RequiredArgsConstructor
public class SeqQuoteHandler extends TextWebSocketHandler {

    private final SeqQuoteService service;
    @Override
    public void afterConnectionEstablished(WebSocketSession ws) throws Exception {
        service.afterConnectionEstablished(ws);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession ws, CloseStatus status) throws Exception {
        service.afterConnectionClosed(ws, status);
    }
}
