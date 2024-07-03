package com.quant_socket.components;

import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.repos.EquitiesSnapshotRepo;
import com.quant_socket.repos.ProductRepo;
import com.quant_socket.repos.SecuritiesQuoteRepo;
import com.quant_socket.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({"local"})
public class LocalScheduledComponent {

    private final SocketLogService socketLogService;
    private final ProductService productService;
    private final IssueClosingService issueClosingService;

    @Scheduled(cron = "30 * * * * ?")
    public void everyMinute() {
        socketLogService.insertLogs(SocketLog.insertCols());
        productService.updateProducts();
        productService.insertLogs();
        issueClosingService.insertLogs();
    }
}
