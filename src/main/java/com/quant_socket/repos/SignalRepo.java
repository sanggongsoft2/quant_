package com.quant_socket.repos;

import com.quant_socket.models.Signal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class SignalRepo extends SG_repo<Signal>{

    public Signal findLastestSignal() {
        final String sql = """
                SELECT * FROM signal_value
                ORDER BY s_idx DESC
                LIMIT 1
                """;
        return super.jt.queryForObject(sql, (rs, rn) -> new Signal(rs));
    }
}
