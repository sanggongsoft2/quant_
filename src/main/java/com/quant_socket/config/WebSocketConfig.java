package com.quant_socket.config;

import com.quant_socket.handlers.EquitiesSnapshotDetailsHandler;
import com.quant_socket.handlers.EquitiesSnapshotHandler;
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

    // WebSocketHandler 에 관한 생성자 추가
    private final EquitiesSnapshotHandler equitiesSnapshotHandler;
    private final EquitiesSnapshotDetailsHandler equitiesSnapshotDetailsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(equitiesSnapshotHandler, "/ws/equities_snapshot").setAllowedOrigins("*");
        registry.addHandler(equitiesSnapshotDetailsHandler, "/ws/equities_snapshot/details").setAllowedOrigins("*");
    }
}