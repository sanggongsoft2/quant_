package com.quant_socket.services;

import com.quant_socket.models.Logs.InvestorActivitiesEOD;
import com.quant_socket.models.Logs.prod.ProductAVG;
import com.quant_socket.models.Product;
import com.quant_socket.repos.ProductAVGRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class ProductAVGService extends SocketService {

    public void dataHandler(ProductAVG data) {
        sendMessage(data);
        sendMessage(data, data.getIsinCode());
    }
}
