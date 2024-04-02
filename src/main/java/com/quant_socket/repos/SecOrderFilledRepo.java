package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.SecOrderFilled;
import org.springframework.stereotype.Repository;

@Repository
public class SecOrderFilledRepo extends SG_repo<SecOrderFilled> {

    public boolean insert(DataSetter setter) {
        return super.insert(SecOrderFilled.class, setter);
    }
}
