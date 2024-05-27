package com.quant_socket.components;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.*;
import com.quant_socket.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledComponent {
    private final EquitiesSnapshotService equitiesSnapshotService;
    private final EquityIndexIndicatorService equityIndexIndicatorService;
    private final SocketLogService socketLogService;
    private final SecuritiesOrderFilledService securitiesOrderFilledService;
    private final InvestActivitiesEODService investActivitiesEODService;
    private final EquitiesBatchDataService equitiesBatchDataService;
    private final ProductService productService;
    private final SeqQuoteService seqQuoteService;

    private final EquitiesSnapshotRepo equitiesSnapshotRepo;
    private final EquityIndexIndicatorRepo equityIndexIndicatorRepo;
    private final SocketLogRepo socketLogRepo;
    private final SecOrderFilledRepo secOrderFilledRepo;
    private final InvestActivitiesEODRepo investActivitiesEODRepo;
    private final EquitiesBatchDataRepo equitiesBatchDataRepo;
    private final ProductRepo productRepo;
    private final SeqQuoteRepo seqQuoteRepo;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void everyMinute() {
        socketLogService.insertLogs(SocketLog.insertCols(), SocketLog.class, socketLogRepo);
        equitiesSnapshotService.insertLogs(EquitiesSnapshot.insertCols(), EquitiesSnapshot.class, equitiesSnapshotRepo);
        equityIndexIndicatorService.insertLogs(EquityIndexIndicator.insertCols(), EquityIndexIndicator.class, equityIndexIndicatorRepo);
        securitiesOrderFilledService.insertLogs(SecOrderFilled.insertCols(), SecOrderFilled.class, secOrderFilledRepo);
        investActivitiesEODService.insertLogs(InvestorActivitiesEOD.insertCols(), InvestorActivitiesEOD.class, investActivitiesEODRepo);
        equitiesBatchDataService.insertLogs(EquitiesBatchData.insertCols(), EquitiesBatchData.class, equitiesBatchDataRepo);
        seqQuoteService.insertLogs(SeqQuote.insertCols(), SeqQuote.class, seqQuoteRepo);
        productService.insertLogs(Product.insertCols(), Product.class, productRepo);
    }

    @Scheduled(cron = "0 * 10-17 * * MON-FRI")
    public void everyMinuteFrom10To15() {
        productService.updateProductMinute();
    }

    @Scheduled(cron = "0 0-1 18 * * MON-FRI")
    public void at15() {
        productService.updateProductMinute();
    }

    @Scheduled(cron = "0 59 11 * * MON-FRI")
    public void everyday1PM() {
        productService.updateProducts();
        productService.updateProductDay();
    }

    @Scheduled(cron = "0 59 11 * * FRI")
    public void everyFriday() {
        productService.updateProductWeek();
    }
}
