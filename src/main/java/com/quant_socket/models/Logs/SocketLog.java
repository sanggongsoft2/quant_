package com.quant_socket.models.Logs;

import com.quant_socket.annotations.*;
import com.quant_socket.models.SG_model;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.Timestamp;

@Data
@SG_table(name = "socket_log")
@RequiredArgsConstructor
public class SocketLog extends SG_model<SocketLog> {

    @SG_idx
    @SG_column(dbField = "SL_idx")
    private Long idx;

    @SG_column(dbField = "SL_log")
    private String log;

    @SG_column(dbField = "SL_remote_url")
    private String remote_url;

    @SG_column(dbField = "SL_port")
    private Integer port;

    @SG_crdt
    @SG_column(dbField = "SL_crdt")
    private Timestamp createdAt;

    @SG_column(dbField = "SL_updt")
    private Timestamp updatedAt;

    @SG_dldt
    @SG_column(dbField = "SL_dldt")
    private Timestamp deletedAt;

    public SocketLog(ResultSet res) {
        super.resultSetToClass(res);
    }
}
