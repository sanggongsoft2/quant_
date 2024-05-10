package com.quant_socket.services;

import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Logs.EquityIndexIndicator;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquityIndexIndicatorRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class EquityIndexIndicatorService extends SocketService<EquityIndexIndicator>{
    @Autowired
    private ProductService productService;
    public void dataHandler(EquityIndexIndicator data) {
        super.addLog(data);

        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) productService.update(data);
        sendMessage(data);
        sendMessage(data, data.getIsin_code());
    }
}
