package com.quant_socket.components;

import com.quant_socket.models.Logs.*;
import com.quant_socket.models.Product;
import com.quant_socket.repos.*;
import com.quant_socket.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"dev", "prod"})
public class ScheduledComponent {

    private final SocketLogService socketLogService;
    private final ProductService productService;
    private final EquitiesSnapshotService equitiesSnapshotService;
    private final SecuritiesQuoteService securitiesQuoteService;
    private final IssueClosingService issueClosingService;

    private final SecuritiesQuoteRepo securitiesQuoteRepo;
    private final ProductRepo productRepo;
    private final EquitiesSnapshotRepo equitiesSnapshotRepo;

    @Scheduled(cron = "0 * * * * ?")
    public void everyMinute() {
        socketLogService.insertLogs(SocketLog.insertCols());
        productService.insertLogs();
        issueClosingService.insertLogs();
    }

    @Profile({"prod", "dev"})
    @Scheduled(cron = "0 * 9-14 * * MON-FRI")
    @Scheduled(cron = "0 0-31 15 * * MON-FRI")
    public void everyMinuteFrom9To15() {
        productService.updateProductMinute();
        equitiesSnapshotService.insertLogs();
        securitiesQuoteService.insertLogs();
    }

    @Profile({"prod", "dev"})
    @Scheduled(cron = "0 0 0 * * MON-FRI")
    public void everyWeekday() {
        productService.updateProducts();
        equitiesSnapshotRepo.deleteLogsFrom3Days();
        securitiesQuoteRepo.deleteBefore1Day();
    }

    @Profile({"prod", "dev"})
    @Scheduled(cron = "0 0 0 * * *")
    public void everyday() {
        productRepo.deleteProductMinuteFrom3Month();
        productService.refreshProducts();
    }

    @Profile({"prod", "dev"})
    @Scheduled(cron = "0 0 0 * * SAT")
    public void everyFriday() {
        productRepo.insertProductWeek();
    }

    @Profile({"prod", "dev"})
    @Scheduled(cron = "0 0 0 1 * ?")
    public void everyMonth() {
        productRepo.insertProductMonth();
    }
}
