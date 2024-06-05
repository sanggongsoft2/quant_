package com.quant_socket.services;

import com.quant_socket.models.Logs.EquitiesBatchData;
import com.quant_socket.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EquitiesBatchDataService{
    @Autowired
    private ProductService productService;

    public void dataHandler(EquitiesBatchData data) {
        /*super.addLog(data);*/

        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) {
            productService.update(data);
        } else {
            productService.addLog(new Product(data));
        }
    }
}
