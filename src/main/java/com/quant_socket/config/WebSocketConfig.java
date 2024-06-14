package com.quant_socket.config;

import com.quant_socket.handlers.socket.EquitiesSnapshotHandler;
import com.quant_socket.handlers.socket.SecOrderFilledHandler;
import com.quant_socket.handlers.socket.SecuritiesQuoteHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    private final EquitiesSnapshotHandler equitiesSnapshotDetailsHandler;

    private final SecOrderFilledHandler secOrderFilledDetailsHandler;
    private final SecuritiesQuoteHandler securitiesQuoteHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(equitiesSnapshotDetailsHandler, "/ws/equities_snapshot").setAllowedOrigins("*");
        registry.addHandler(secOrderFilledDetailsHandler, "/ws/sec_order_filled").setAllowedOrigins("*");
        registry.addHandler(securitiesQuoteHandler, "/ws/securities_quote").setAllowedOrigins("*");
    }
}