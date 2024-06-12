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

    private final SocketLogRepo socketLogRepo;
    private final ProductRepo productRepo;

    /*@Scheduled(cron = "* * * * * *")
    public void everySeconds() {
        log.info("CURRENT TIMESTAMP : {}", Timestamp.from(Instant.now()));
    }*/

    @Scheduled(cron = "0 * * * * ?")
    public void everyMinute() {
        socketLogService.insertLogs(SocketLog.insertCols());
        productService.insertLogs(Product.insertCols());
    }

    @Scheduled(cron = "0 * 10-14 * * MON-FRI")
    @Scheduled(cron = "0 0-30 15 * * MON-FRI")
    public void everyMinuteFrom10To15() {
        productService.updateProductMinute();
        equitiesSnapshotService.insertLogs();
    }

    @Scheduled(cron = "0 59 23 * * MON-FRI")
    public void everyWeekday() {
        productService.updateProducts();
        productService.updateProductDay();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void everyday() {
        socketLogRepo.deleteLogsFrom3Days();
        productRepo.deleteProductMinuteFrom3Month();
    }

    @Scheduled(cron = "0 0 0 * * SAT")
    public void everyFriday() {
        productRepo.insertProductWeek();
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void everyMonth() {
        productRepo.insertProductMonth();
    }
}
