package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.InvestorActivitiesEOD;
import com.quant_socket.models.Logs.prod.ProductMinute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Repository
@Slf4j
public class ProductMinuteRepo extends SG_repo<ProductMinute>{
    public boolean insert(DataSetter setter) {
        return super.insert(ProductMinute.class, setter);
    }
}
