package com.quant_socket.models.Logs;

import com.quant_socket.annotations.*;
import com.quant_socket.models.SG_model;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@SG_table(name = "socket_log")
@ToString
public class SocketLog extends SG_model {

    @SG_idx
    @SG_column(dbField = "SL_idx")
    private Long idx;

    @SG_column(dbField = "SL_log")
    private String log;

    @SG_column(dbField = "SL_remote_url")
    private String remote_url;

    @SG_column(dbField = "SL_port")
    private Integer port;

    @SG_column(dbField = "SL_error")
    private String error;

    @SG_column(dbField = "SL_crdt")
    private Timestamp createdAt;

    public SocketLog() {

    }

    public SocketLog(ResultSet res) {
        super(res);
    }

    static public String[] insertCols() {
        return new String[] {
                "SL_log",
                "SL_remote_url",
                "SL_port",
                "SL_error",
        };
    }
}
