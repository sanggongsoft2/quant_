package com.quant_socket.components;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.*;
import com.quant_socket.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedRate = 60000)
    public void everyMinute() {
        equitiesSnapshotService.insertLogs(EquitiesSnapshot.class, equitiesSnapshotRepo);
        equityIndexIndicatorService.insertLogs(EquityIndexIndicator.class, equityIndexIndicatorRepo);
        socketLogService.insertLogs(SocketLog.class, socketLogRepo);
        securitiesOrderFilledService.insertLogs(SecOrderFilled.class, secOrderFilledRepo);
        investActivitiesEODService.insertLogs(InvestorActivitiesEOD.class, investActivitiesEODRepo);
        equitiesBatchDataService.insertLogs(EquitiesBatchData.class, equitiesBatchDataRepo);
        seqQuoteService.insertLogs(SeqQuote.class, seqQuoteRepo);
        productService.insertLogs(Product.class, productRepo);;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void everyday1PM() {
        productService.refreshProductItems();
    }
}
