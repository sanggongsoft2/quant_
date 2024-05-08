package com.quant_socket.components;

import com.quant_socket.repos.SocketLogRepo;
import com.quant_socket.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledComponent {
    private final SocketLogRepo repo;
    private final EquitiesSnapshotService equitiesSnapshotService;
    private final EquityIndexIndicatorService equityIndexIndicatorService;
    private final SocketLogService socketLogService;
    private final SecuritiesOrderFilledService securitiesOrderFilledService;
    private final InvestActivitiesEODService investActivitiesEODService;
    private final EquitiesBatchDataService equitiesBatchDataService;
    private final ProductService productService;
    private final SeqQuoteService seqQuoteService;

    @Scheduled(fixedRate = 60000)
    public void everyMinute() {
        equitiesSnapshotService.insertLogs();
        equityIndexIndicatorService.insertLogs();
        socketLogService.insertLogs();
        securitiesOrderFilledService.insertLogs();
        investActivitiesEODService.insertLogs();
        equitiesBatchDataService.insertLogs();
        seqQuoteService.insertLogs();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void everyday1PM() {
        productService.refreshProductItems();
    }
}
