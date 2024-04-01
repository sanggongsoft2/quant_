package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Logs.EquityIndexIndicator;
import org.springframework.stereotype.Repository;

@Repository
public class EquityIndexIndicatorRepo extends SG_repo<EquityIndexIndicator> {

    public boolean insert(DataSetter setter) {
        return super.insert(EquityIndexIndicator.class, setter);
    }
}
