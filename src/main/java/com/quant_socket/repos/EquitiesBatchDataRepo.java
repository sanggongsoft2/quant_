package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.EquitiesBatchData;
import org.springframework.stereotype.Repository;

@Repository
public class EquitiesBatchDataRepo extends SG_repo<EquitiesBatchData> {

    public boolean insert(DataSetter setter) {
        return super.insert(EquitiesBatchData.class, setter);
    }
}
