package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Logs.InvestorActivitiesEOD;
import com.quant_socket.models.Logs.SocketLog;
import com.quant_socket.models.Product;
import com.quant_socket.repos.InvestActivitiesEODRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class InvestActivitiesEODService extends SocketService<InvestorActivitiesEOD>{
    @Autowired
    private ProductService productService;
    public void dataHandler(InvestorActivitiesEOD data) {
        super.addLog(data);

        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) {
            productService.update(data);
            sendMessage(data.toSocket(product));
            sendMessage(data.toSocket(product), data.getIsin_code());
        }
    }
}
