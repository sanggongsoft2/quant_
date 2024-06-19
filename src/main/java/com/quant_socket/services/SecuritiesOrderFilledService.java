package com.quant_socket.services;

import com.quant_socket.models.Logs.SecOrderFilled;
import com.quant_socket.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;


@Service
@Slf4j
public class SecuritiesOrderFilledService extends SocketService{
    @Autowired
    private ProductService productService;
    public void dataHandler(SecOrderFilled data) {
        if(data.getIsin_code() != null) {
            final Product product = productService.productFromIsinCode(data.getIsin_code());
            if(product != null) {
                product.update(data);
                sendMessage(data.toSocket(product), data.getIsin_code());
            }
        }
    }

    @Override
    public void addSession(WebSocketSession ws, String... isinCodes) {
        super.addSession(ws, isinCodes);
        for(String isinCode : isinCodes) {
            final Product prod = productService.productFromIsinCode(isinCode);
            if(prod != null) sendMessage(prod.getOrders().stream().map(order -> order.toSocket(prod)).toList(), isinCode);
        }
    }
}
