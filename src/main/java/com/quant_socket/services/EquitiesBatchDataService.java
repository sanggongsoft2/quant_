package com.quant_socket.services;

import com.quant_socket.models.Logs.EquitiesBatchData;
import com.quant_socket.models.Logs.SecOrderFilled;
import com.quant_socket.models.Product;
import com.quant_socket.repos.EquitiesBatchDataRepo;
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
public class EquitiesBatchDataService extends SocketService<EquitiesBatchData>{
    @Autowired
    private ProductService productService;
    public void dataHandler(EquitiesBatchData data) {
        /*super.addLog(data);*/

        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) {
            productService.update(data);
            sendMessage(data.toSocket(product));
            sendMessage(data.toSocket(product), data.getIsin_code());
        } else {
            productService.addLog(new Product(data));
        }
    }
}
