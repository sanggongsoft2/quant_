package com.quant_socket.models;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.ResultSet;

@SG_table(name = "product")
@Getter
@Setter
@RequiredArgsConstructor
public class Product extends SG_model<Product>{

    @SG_idx
    @SG_column(dbField = "p_idx")
    private Long idx;
    @SG_column(dbField = "p_code")
    private String code;
    @SG_column(dbField = "p_short_code")
    private String short_code;
    @SG_column(dbField = "p_name_kr")
    private String name_kr;
    @SG_column(dbField = "p_name_kr_abbr")
    private String name_kr_abbr;
    @SG_column(dbField = "p_name_en")
    private String name_en;
    @SG_column(dbField = "p_class")
    private String gubun;
    @SG_column(dbField = "p_seq_class")
    private String seq_gubun;
    @SG_column(dbField = "p_team")
    private String team;
    @SG_column(dbField = "p_type")
    private String type;
    @SG_column(dbField = "p_face_value")
    private String face_value;
    @SG_column(dbField = "p_having_count")
    private String having_count;
    @SG_crdt
    @SG_column(dbField = "p_crdt")
    private String crdt;

    public Product(ResultSet rs) {
        super.resultSetToClass(rs);
    }
}
