package com.quant_socket.components;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.*;
import com.quant_socket.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledComponent {
    private final SocketLogService socketLogService;
    private final ProductService productService;
    private final EquitiesSnapshotService equitiesSnapshotService;
    private final SecuritiesQuoteService securitiesQuoteService;
    private final IssueClosingService issueClosingService;

    private final SocketLogRepo socketLogRepo;
    private final ProductRepo productRepo;
    private final EquitiesSnapshotRepo equitiesSnapshotRepo;

    /*@Scheduled(cron = "* * * * * *")
    public void everySeconds() {
        log.info("CURRENT TIMESTAMP : {}", Timestamp.from(Instant.now()));
    }*/

    @Scheduled(cron = "0 * * * * ?")
    public synchronized void everyMinute() {
        socketLogService.insertLogs(SocketLog.insertCols());
        productService.insertLogs(Product.insertCols());
        issueClosingService.insertLogs();
    }

    /*@Scheduled(cron = "0 * 9-14 * * MON-FRI")
    @Scheduled(cron = "0 0-30 15 * * MON-FRI")
    public void everyMinuteFrom9To15() {
        productService.updateProductMinute();
        equitiesSnapshotService.insertLogs();
        securitiesQuoteService.insertLogs();
    }*/

    @Scheduled(cron = "0 59 23 * * MON-FRI")
    public synchronized void everyWeekday() {
        productService.updateProducts();
        /*productService.updateProductDay();*/
        equitiesSnapshotRepo.deleteLogsFrom3Days();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public synchronized void everyday() {
        socketLogRepo.deleteLogsFrom3Days();
        productRepo.deleteProductMinuteFrom3Month();
    }

    @Scheduled(cron = "0 0 0 * * SAT")
    public synchronized void everyFriday() {
        productRepo.insertProductWeek();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public synchronized void everyMonth() {
        productRepo.insertProductMonth();
    }
}
