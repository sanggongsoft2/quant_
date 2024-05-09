package com.quant_socket.repos;

import com.quant_socket.annotations.SG_table;
import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.SocketLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public abstract class SG_repo<T> {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected JdbcTemplate jt;

    protected boolean insert(Class<T> clazz, DataSetter setter) {
        final Map<String, Object> data = new HashMap<>();
        final String table = getTableName(clazz);

        setter.setData(data);

        final StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(table)
                .append("(")
                .append(String.join(",", data.keySet()))
                .append(")")
                .append("\nVALUES(")
                .append(data.values().stream().map((value) -> "?").collect(Collectors.joining(",")))
                .append(")");

        log.debug("INSERT SQL = {}", sb);

        final int rows = this.jt.update(sb.toString(), data.values().toArray());
        return rows > 0;
    }

    public Long insertReturnKey(Class<T> clazz, DataSetter setter) {
        final Map<String, Object> data = new HashMap<>();
        final String table = getTableName(clazz);

        setter.setData(data);

        final SimpleJdbcInsert insert = new SimpleJdbcInsert(jt)
                .withTableName(table)
                .usingColumns(data.keySet().toArray(new String[data.size()]))
                .usingGeneratedKeyColumns("id");

        final SqlParameterSource parameter = new MapSqlParameterSource()
                .addValues(data);

        return insert.executeAndReturnKey(parameter).longValue();
    }

    private String getTableName(Class<?> clazz) {
        String table = clazz.getSimpleName().toLowerCase();
        if(clazz.isAnnotationPresent(SG_table.class)) table = clazz.getAnnotation(SG_table.class).name();
        return table;
    }

    public int insertMany(String sql, BatchPreparedStatementSetter setter){
        jt.batchUpdate(sql, setter);
        return setter.getBatchSize();
    }
}
