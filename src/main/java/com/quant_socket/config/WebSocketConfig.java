package com.quant_socket.config;

import com.quant_socket.handlers.socket.equities_snapshot.EquitiesSnapshotDetailsHandler;
import com.quant_socket.handlers.socket.equities_snapshot.EquitiesSnapshotHandler;
import com.quant_socket.handlers.socket.sec_order_filled.SecOrderFilledDetailsHandler;
import com.quant_socket.handlers.socket.sec_order_filled.SecOrderFilledHandler;
import com.quant_socket.handlers.socket.seq_quote.SeqQuoteDetailsHandler;
import com.quant_socket.handlers.socket.seq_quote.SeqQuoteHandler;
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

    private final EquitiesSnapshotHandler equitiesSnapshotHandler;
    private final EquitiesSnapshotDetailsHandler equitiesSnapshotDetailsHandler;

    private final SecOrderFilledHandler secOrderFilledHandler;
    private final SecOrderFilledDetailsHandler secOrderFilledDetailsHandler;

    private final SeqQuoteHandler seqQuoteHandler;
    private final SeqQuoteDetailsHandler seqQuoteDetailsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(equitiesSnapshotHandler, "/ws/equities_snapshot").setAllowedOrigins("*");
        registry.addHandler(equitiesSnapshotDetailsHandler, "/ws/equities_snapshot/details").setAllowedOrigins("*");

        registry.addHandler(secOrderFilledHandler, "/ws/sec_order_filled").setAllowedOrigins("*");
        registry.addHandler(secOrderFilledDetailsHandler, "/ws/sec_order_filled/details").setAllowedOrigins("*");

        registry.addHandler(seqQuoteHandler, "/ws/seq_quote").setAllowedOrigins("*");
        registry.addHandler(seqQuoteDetailsHandler, "/ws/seq_quote/details").setAllowedOrigins("*");
    }
}