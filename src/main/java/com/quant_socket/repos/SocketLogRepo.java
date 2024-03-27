package com.quant_socket.repos;

import com.quant_socket.models.Logs.SocketLog;
import org.springframework.stereotype.Repository;

@Repository
public class SocketLogRepo extends SG_repo<SocketLog> {
    public boolean insert(String msg, Integer port, String remote_url) {
        return super.insert(SocketLog.class, (data) -> {
            data.put("SL_log", msg);
            data.put("SL_port", port);
            data.put("SL_remote_url", remote_url);
        });
    }
}
