package com.quant_socket.config;

import com.quant_socket.handlers.socket.equities_batch_data.EquitiesBatchDataDetailsHandler;
import com.quant_socket.handlers.socket.equities_batch_data.EquitiesBatchDataHandler;
import com.quant_socket.handlers.socket.equities_index_indicator.EquitiesIndexIndicatorDetailsHandler;
import com.quant_socket.handlers.socket.equities_index_indicator.EquitiesIndexIndicatorHandler;
import com.quant_socket.handlers.socket.equities_snapshot.EquitiesSnapshotDetailsHandler;
import com.quant_socket.handlers.socket.equities_snapshot.EquitiesSnapshotHandler;
import com.quant_socket.handlers.socket.investor_activities_eod.InvestorActivitiesEODDetailsHandler;
import com.quant_socket.handlers.socket.investor_activities_eod.InvestorActivitiesEODHandler;
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

    // WebSocketHandler 에 관한 생성자 추가
    private final EquitiesBatchDataHandler equitiesBatchDataHandler;
    private final EquitiesBatchDataDetailsHandler equitiesBatchDataDetailsHandler;

    private final EquitiesIndexIndicatorHandler equitiesIndexIndicatorHandler;
    private final EquitiesIndexIndicatorDetailsHandler equitiesIndexIndicatorDetailsHandler;

    private final EquitiesSnapshotHandler equitiesSnapshotHandler;
    private final EquitiesSnapshotDetailsHandler equitiesSnapshotDetailsHandler;

    private final InvestorActivitiesEODHandler investorActivitiesEODHandler;
    private final InvestorActivitiesEODDetailsHandler investorActivitiesEODDetailsHandler;

    private final SecOrderFilledHandler secOrderFilledHandler;
    private final SecOrderFilledDetailsHandler secOrderFilledDetailsHandler;

    private final SeqQuoteHandler seqQuoteHandler;
    private final SeqQuoteDetailsHandler seqQuoteDetailsHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(equitiesBatchDataHandler, "/ws/equities_batch_data").setAllowedOrigins("*");
        registry.addHandler(equitiesBatchDataDetailsHandler, "/ws/equities_batch_data/details").setAllowedOrigins("*");

        registry.addHandler(equitiesIndexIndicatorHandler, "/ws/equities_index_indicator").setAllowedOrigins("*");
        registry.addHandler(equitiesIndexIndicatorDetailsHandler, "/ws/equities_index_indicator/details").setAllowedOrigins("*");

        registry.addHandler(equitiesSnapshotHandler, "/ws/equities_snapshot").setAllowedOrigins("*");
        registry.addHandler(equitiesSnapshotDetailsHandler, "/ws/equities_snapshot/details").setAllowedOrigins("*");

        registry.addHandler(investorActivitiesEODHandler, "/ws/investor_activities_eod").setAllowedOrigins("*");
        registry.addHandler(investorActivitiesEODDetailsHandler, "/ws/investor_activities_eod/details").setAllowedOrigins("*");

        registry.addHandler(secOrderFilledHandler, "/ws/sec_order_filled").setAllowedOrigins("*");
        registry.addHandler(secOrderFilledDetailsHandler, "/ws/sec_order_filled/details").setAllowedOrigins("*");

        registry.addHandler(seqQuoteHandler, "/ws/seq_quote").setAllowedOrigins("*");
        registry.addHandler(seqQuoteDetailsHandler, "/ws/seq_quote/details").setAllowedOrigins("*");
    }
}