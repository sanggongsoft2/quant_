package com.quant_socket.models.Logs;

import com.quant_socket.annotations.SG_column;
import com.quant_socket.annotations.SG_crdt;
import com.quant_socket.annotations.SG_idx;
import com.quant_socket.annotations.SG_table;
import com.quant_socket.models.Product;
import com.quant_socket.models.SG_model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@SG_table(name = "seq_quote")
@ToString
public class SeqQuote extends SG_model<SeqQuote> {
    @SG_idx
    @SG_column(dbField = "sq_idx")
    private Long idx;
    @SG_column(dbField = "sq_data_category")
    private String data_category;
    @SG_column(dbField = "sq_info_category")
    private String info_category;
    @SG_column(dbField = "sq_message_seq_number")
    private Integer message_seq_number;
    @SG_column(dbField = "sq_board_id")
    private String board_id;
    @SG_column(dbField = "sq_session_id")
    private String session_id;
    @SG_column(dbField = "sq_isin_code")
    private String isin_code;
    @SG_column(dbField = "sq_a_designated_number_for_an_issue")
    private Integer a_des_number_for_an_issue;
    @SG_column(dbField = "sq_processing_time_of_trading_system")
    private String processing_time_of_trading_system;

    @SG_column(dbField = "sq_ask_level1_price")
    private Double ask_level1_price;
    @SG_column(dbField = "sq_bid_level1_price")
    private Double bid_level1_price;
    @SG_column(dbField = "sq_ask_level1_volume")
    private Long ask_level1_volume;
    @SG_column(dbField = "sq_bid_level1_volume")
    private Long bid_level1_volume;

    @SG_column(dbField = "sq_ask_level2_price")
    private Double ask_level2_price;
    @SG_column(dbField = "sq_bid_level2_price")
    private Double bid_level2_price;
    @SG_column(dbField = "sq_ask_level2_volume")
    private Long ask_level2_volume;
    @SG_column(dbField = "sq_bid_level2_volume")
    private Long bid_level2_volume;

    @SG_column(dbField = "sq_ask_level3_price")
    private Double ask_level3_price;
    @SG_column(dbField = "sq_bid_level3_price")
    private Double bid_level3_price;
    @SG_column(dbField = "sq_ask_level3_volume")
    private Long ask_level3_volume;
    @SG_column(dbField = "sq_bid_level3_volume")
    private Long bid_level3_volume;

    @SG_column(dbField = "sq_ask_level4_price")
    private Double ask_level4_price;
    @SG_column(dbField = "sq_bid_level4_price")
    private Double bid_level4_price;
    @SG_column(dbField = "sq_ask_level4_volume")
    private Long ask_level4_volume;
    @SG_column(dbField = "sq_bid_level4_volume")
    private Long bid_level4_volume;

    @SG_column(dbField = "sq_ask_level5_price")
    private Double ask_level5_price;
    @SG_column(dbField = "sq_bid_level5_price")
    private Double bid_level5_price;
    @SG_column(dbField = "sq_ask_level5_volume")
    private Long ask_level5_volume;
    @SG_column(dbField = "sq_bid_level5_volume")
    private Long bid_level5_volume;

    @SG_column(dbField = "sq_ask_level6_price")
    private Double ask_level6_price;
    @SG_column(dbField = "sq_bid_level6_price")
    private Double bid_level6_price;
    @SG_column(dbField = "sq_ask_level6_volume")
    private Long ask_level6_volume;
    @SG_column(dbField = "sq_bid_level6_volume")
    private Long bid_level6_volume;

    @SG_column(dbField = "sq_ask_level7_price")
    private Double ask_level7_price;
    @SG_column(dbField = "sq_bid_level7_price")
    private Double bid_level7_price;
    @SG_column(dbField = "sq_ask_level7_volume")
    private Long ask_level7_volume;
    @SG_column(dbField = "sq_bid_level7_volume")
    private Long bid_level7_volume;

    @SG_column(dbField = "sq_ask_level8_price")
    private Double ask_level8_price;
    @SG_column(dbField = "sq_bid_level8_price")
    private Double bid_level8_price;
    @SG_column(dbField = "sq_ask_level8_volume")
    private Long ask_level8_volume;
    @SG_column(dbField = "sq_bid_level8_volume")
    private Long bid_level8_volume;

    @SG_column(dbField = "sq_ask_level9_price")
    private Double ask_level9_price;
    @SG_column(dbField = "sq_bid_level9_price")
    private Double bid_level9_price;
    @SG_column(dbField = "sq_ask_level9_volume")
    private Long ask_level9_volume;
    @SG_column(dbField = "sq_bid_level9_volume")
    private Long bid_level9_volume;

    @SG_column(dbField = "sq_ask_level10_price")
    private Double ask_level10_price;
    @SG_column(dbField = "sq_bid_level10_price")
    private Double bid_level10_price;
    @SG_column(dbField = "sq_ask_level10_volume")
    private Long ask_level10_volume;
    @SG_column(dbField = "sq_bid_level10_volume")
    private Long bid_level10_volume;

    @SG_column(dbField = "sq_total_ask_volume")
    private Long total_ask_volume;
    @SG_column(dbField = "sq_total_bid_volume")
    private Long total_bid_volume;
    @SG_column(dbField = "sq_estimated_tading_price")
    private Double estimated_tading_price;
    @SG_column(dbField = "sq_estimated_trading_volume")
    private Long estimated_trading_volume;

    @SG_column(dbField = "sq_end_keyword")
    private String end_keyword;
    @SG_crdt
    @SG_column(dbField = "sq_crdt")
    private Timestamp createdAt;

    public SeqQuote(String msg) {
        data_category = msg.substring(0, 2);
        info_category = msg.substring(2, 5);
        if(!msg.substring(5, 13).isBlank()) message_seq_number = Integer.parseInt(msg.substring(5, 13));
        board_id = msg.substring(13, 15);
        session_id = msg.substring(15, 17);
        isin_code = msg.substring(17, 29);
        if(!msg.substring(29, 35).isBlank()) a_des_number_for_an_issue = Integer.parseInt(msg.substring(29, 35));
        processing_time_of_trading_system = msg.substring(35, 47);

        if(!msg.substring(47, 58).isBlank()) ask_level1_price = Double.parseDouble(msg.substring(47, 58));
        if(!msg.substring(58, 69).isBlank()) bid_level1_price = Double.parseDouble(msg.substring(58,69));
        if(!msg.substring(69, 81).isBlank()) ask_level1_volume = Long.parseLong(msg.substring(69, 81));
        if(!msg.substring(81, 93).isBlank()) bid_level1_volume = Long.parseLong(msg.substring(81, 93));

        if(!msg.substring(93, 104).isBlank()) ask_level2_price = Double.parseDouble(msg.substring(93, 104));
        if(!msg.substring(104, 115).isBlank()) bid_level2_price = Double.parseDouble(msg.substring(104, 115));
        if(!msg.substring(115, 127).isBlank()) ask_level2_volume = Long.parseLong(msg.substring(115, 127));
        if(!msg.substring(127, 139).isBlank()) bid_level2_volume = Long.parseLong(msg.substring(127, 139));

        if(!msg.substring(139, 150).isBlank()) ask_level3_price = Double.parseDouble(msg.substring(139, 150));
        if(!msg.substring(150, 161).isBlank()) bid_level3_price = Double.parseDouble(msg.substring(150, 161));
        if(!msg.substring(161, 173).isBlank()) ask_level3_volume = Long.parseLong(msg.substring(161, 173));
        if(!msg.substring(173, 185).isBlank()) bid_level3_volume = Long.parseLong(msg.substring(173, 185));

        if(!msg.substring(185, 196).isBlank()) ask_level4_price = Double.parseDouble(msg.substring(185, 196));
        if(!msg.substring(196, 207).isBlank()) bid_level4_price = Double.parseDouble(msg.substring(196, 207));
        if(!msg.substring(207, 219).isBlank()) ask_level4_volume = Long.parseLong(msg.substring(207, 219));
        if(!msg.substring(219, 231).isBlank()) bid_level4_volume = Long.parseLong(msg.substring(219, 231));

        if(!msg.substring(231, 242).isBlank()) ask_level5_price = Double.parseDouble(msg.substring(231, 242));
        if(!msg.substring(242, 253).isBlank()) bid_level5_price = Double.parseDouble(msg.substring(242, 253));
        if(!msg.substring(253, 265).isBlank()) ask_level5_volume = Long.parseLong(msg.substring(253, 265));
        if(!msg.substring(265, 277).isBlank()) bid_level5_volume = Long.parseLong(msg.substring(265, 277));

        if(!msg.substring(277, 288).isBlank()) ask_level6_price = Double.parseDouble(msg.substring(277, 288));
        if(!msg.substring(288, 299).isBlank()) bid_level6_price = Double.parseDouble(msg.substring(288, 299));
        if(!msg.substring(299, 311).isBlank()) ask_level6_volume = Long.parseLong(msg.substring(299, 311));
        if(!msg.substring(311, 323).isBlank()) bid_level6_volume = Long.parseLong(msg.substring(311, 323));

        if(!msg.substring(323, 334).isBlank()) ask_level7_price = Double.parseDouble(msg.substring(323, 334));
        if(!msg.substring(334, 345).isBlank()) bid_level7_price = Double.parseDouble(msg.substring(334, 345));
        if(!msg.substring(345, 357).isBlank()) ask_level7_volume = Long.parseLong(msg.substring(345, 357));
        if(!msg.substring(357, 369).isBlank()) bid_level7_volume = Long.parseLong(msg.substring(357, 369));

        if(!msg.substring(369, 380).isBlank()) ask_level8_price = Double.parseDouble(msg.substring(369, 380));
        if(!msg.substring(380, 391).isBlank()) bid_level8_price = Double.parseDouble(msg.substring(380, 391));
        if(!msg.substring(391, 403).isBlank()) ask_level8_volume = Long.parseLong(msg.substring(391, 403));
        if(!msg.substring(403, 415).isBlank()) bid_level8_volume = Long.parseLong(msg.substring(403, 415));

        if(!msg.substring(415, 426).isBlank()) ask_level9_price = Double.parseDouble(msg.substring(415, 426));
        if(!msg.substring(426, 437).isBlank()) bid_level9_price = Double.parseDouble(msg.substring(426, 437));
        if(!msg.substring(437, 449).isBlank()) ask_level9_volume = Long.parseLong(msg.substring(437, 449));
        if(!msg.substring(449, 461).isBlank()) bid_level9_volume = Long.parseLong(msg.substring(449, 461));

        if(!msg.substring(461, 472).isBlank()) ask_level10_price = Double.parseDouble(msg.substring(461, 472));
        if(!msg.substring(472, 483).isBlank()) bid_level10_price = Double.parseDouble(msg.substring(472, 483));
        if(!msg.substring(483, 495).isBlank()) ask_level10_volume = Long.parseLong(msg.substring(483, 495));
        if(!msg.substring(495, 507).isBlank()) bid_level10_volume = Long.parseLong(msg.substring(495, 507));

        if(!msg.substring(507, 519).isBlank()) total_ask_volume = Long.parseLong(msg.substring(507, 519));
        if(!msg.substring(519, 531).isBlank()) total_bid_volume = Long.parseLong(msg.substring(519, 531));
        if(!msg.substring(531, 542).isBlank()) estimated_tading_price = Double.parseDouble(msg.substring(531, 542));
        if(!msg.substring(542, 554).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(542, 554));

        end_keyword = msg.substring(554, 555);
        createdAt = Timestamp.from(Instant.now());
    }

    public Map<String, Object> toSocket() {
        final Map<String, Object> response = new HashMap<>();
        return response;
    }
}
