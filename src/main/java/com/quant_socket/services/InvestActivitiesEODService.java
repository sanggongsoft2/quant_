package com.quant_socket.services;

import com.quant_socket.models.Logs.InvestorActivitiesEOD;
import com.quant_socket.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvestActivitiesEODService{
    @Autowired
    private ProductService productService;
    public void dataHandler(InvestorActivitiesEOD data) {
        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) productService.update(data);
    }
}
