package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.EquitiesSnapshot;
import org.springframework.stereotype.Repository;

@Repository
public class EquitiesSnapshotRepo extends SG_repo<EquitiesSnapshot> {

    public boolean insert(DataSetter setter) {
        return super.insert(EquitiesSnapshot.class, setter);
    }
}
