package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.InvestorActivitiesEOD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class InvestActivitiesEODRepo extends SG_repo<InvestorActivitiesEOD>{
    public boolean insert(DataSetter setter) {
        return super.insert(InvestorActivitiesEOD.class, setter);
    }
}
