package com.quant_socket.repos;

import com.quant_socket.models.Logs.SocketLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SocketLogRepo extends SG_repo<SocketLog> {
    public boolean insert(String msg, Integer port, String remote_url) {
        return this.insert(msg, port, remote_url, null);
    }
    public boolean insert(String msg, Integer port, String remote_url, String error) {
        return super.insert(SocketLog.class, (data) -> {
            data.put("SL_log", msg);
            data.put("SL_port", port);
            data.put("SL_remote_url", remote_url);
            if(error != null) data.put("SL_error", error);
        });
    }

    public SocketLog findOne(String startKeyword, Long idx) {
        final String sql = "SELECT * FROM socket_log WHERE SL_idx > " + idx + " AND SL_log LIKE '"+startKeyword+"%' ORDER BY SL_idx ASC LIMIT 1";
        try {
            return super.jt.queryForObject(sql, (rs, rn) -> new SocketLog(rs));
        } catch (Exception e) {
            return null;
        }
    }
}
