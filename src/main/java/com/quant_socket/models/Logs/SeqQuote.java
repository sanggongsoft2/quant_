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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
@SG_table(name = "seq_quote")
@ToString
public class SeqQuote extends SG_model {
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
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level1_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level1_volume;

    @SG_column(dbField = "sq_ask_level2_price")
    private Double ask_level2_price;
    @SG_column(dbField = "sq_bid_level2_price")
    private Double bid_level2_price;
    @SG_column(dbField = "sq_ask_level2_volume")
    private Long ask_level2_volume;
    @SG_column(dbField = "sq_bid_level2_volume")
    private Long bid_level2_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level2_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level2_volume;

    @SG_column(dbField = "sq_ask_level3_price")
    private Double ask_level3_price;
    @SG_column(dbField = "sq_bid_level3_price")
    private Double bid_level3_price;
    @SG_column(dbField = "sq_ask_level3_volume")
    private Long ask_level3_volume;
    @SG_column(dbField = "sq_bid_level3_volume")
    private Long bid_level3_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level3_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level3_volume;

    @SG_column(dbField = "sq_ask_level4_price")
    private Double ask_level4_price;
    @SG_column(dbField = "sq_bid_level4_price")
    private Double bid_level4_price;
    @SG_column(dbField = "sq_ask_level4_volume")
    private Long ask_level4_volume;
    @SG_column(dbField = "sq_bid_level4_volume")
    private Long bid_level4_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level4_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level4_volume;

    @SG_column(dbField = "sq_ask_level5_price")
    private Double ask_level5_price;
    @SG_column(dbField = "sq_bid_level5_price")
    private Double bid_level5_price;
    @SG_column(dbField = "sq_ask_level5_volume")
    private Long ask_level5_volume;
    @SG_column(dbField = "sq_bid_level5_volume")
    private Long bid_level5_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level5_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level5_volume;

    @SG_column(dbField = "sq_ask_level6_price")
    private Double ask_level6_price;
    @SG_column(dbField = "sq_bid_level6_price")
    private Double bid_level6_price;
    @SG_column(dbField = "sq_ask_level6_volume")
    private Long ask_level6_volume;
    @SG_column(dbField = "sq_bid_level6_volume")
    private Long bid_level6_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level6_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level6_volume;

    @SG_column(dbField = "sq_ask_level7_price")
    private Double ask_level7_price;
    @SG_column(dbField = "sq_bid_level7_price")
    private Double bid_level7_price;
    @SG_column(dbField = "sq_ask_level7_volume")
    private Long ask_level7_volume;
    @SG_column(dbField = "sq_bid_level7_volume")
    private Long bid_level7_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level7_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level7_volume;

    @SG_column(dbField = "sq_ask_level8_price")
    private Double ask_level8_price;
    @SG_column(dbField = "sq_bid_level8_price")
    private Double bid_level8_price;
    @SG_column(dbField = "sq_ask_level8_volume")
    private Long ask_level8_volume;
    @SG_column(dbField = "sq_bid_level8_volume")
    private Long bid_level8_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level8_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level8_volume;

    @SG_column(dbField = "sq_ask_level9_price")
    private Double ask_level9_price;
    @SG_column(dbField = "sq_bid_level9_price")
    private Double bid_level9_price;
    @SG_column(dbField = "sq_ask_level9_volume")
    private Long ask_level9_volume;
    @SG_column(dbField = "sq_bid_level9_volume")
    private Long bid_level9_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level9_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level9_volume;

    @SG_column(dbField = "sq_ask_level10_price")
    private Double ask_level10_price;
    @SG_column(dbField = "sq_bid_level10_price")
    private Double bid_level10_price;
    @SG_column(dbField = "sq_ask_level10_volume")
    private Long ask_level10_volume;
    @SG_column(dbField = "sq_bid_level10_volume")
    private Long bid_level10_volume;
    @SG_column(dbField = "sq_lp_ask_level1_volume")
    private Long ask_lp_level10_volume;
    @SG_column(dbField = "sq_lp_bid_level1_volume")
    private Long bid_lp_level10_volume;

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

    @SG_column(dbField = "sq_crdt")
    private Timestamp createdAt;

    public SeqQuote(String msg) throws NumberFormatException {

        Instant now = Instant.now();
        ZonedDateTime koreaTime = now.atZone(ZoneId.of("Asia/Seoul"));
        createdAt = Timestamp.from(koreaTime.toInstant());

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
        if(!msg.substring(93, 105).isBlank()) ask_lp_level1_volume = Long.parseLong(msg.substring(93, 105));
        if(!msg.substring(105, 117).isBlank()) bid_lp_level1_volume = Long.parseLong(msg.substring(105, 117));

        if(!msg.substring(117, 128).isBlank()) ask_level2_price = Double.parseDouble(msg.substring(117, 128));
        if(!msg.substring(128, 139).isBlank()) bid_level2_price = Double.parseDouble(msg.substring(128, 139));
        if(!msg.substring(139, 151).isBlank()) ask_level2_volume = Long.parseLong(msg.substring(139, 151));
        if(!msg.substring(151, 163).isBlank()) bid_level2_volume = Long.parseLong(msg.substring(151, 163));
        if(!msg.substring(163, 175).isBlank()) ask_lp_level2_volume = Long.parseLong(msg.substring(163, 175));
        if(!msg.substring(175, 187).isBlank()) bid_lp_level2_volume = Long.parseLong(msg.substring(175, 187));

        if(!msg.substring(187, 198).isBlank()) ask_level3_price = Double.parseDouble(msg.substring(187, 198));
        if(!msg.substring(198, 209).isBlank()) bid_level3_price = Double.parseDouble(msg.substring(198, 209));
        if(!msg.substring(209, 221).isBlank()) ask_level3_volume = Long.parseLong(msg.substring(209, 221));
        if(!msg.substring(221, 233).isBlank()) bid_level3_volume = Long.parseLong(msg.substring(221, 233));
        if(!msg.substring(233, 245).isBlank()) ask_lp_level3_volume = Long.parseLong(msg.substring(233, 245));
        if(!msg.substring(245, 257).isBlank()) bid_lp_level3_volume = Long.parseLong(msg.substring(245, 257));

        if(!msg.substring(257, 268).isBlank()) ask_level4_price = Double.parseDouble(msg.substring(257, 268));
        if(!msg.substring(268, 279).isBlank()) bid_level4_price = Double.parseDouble(msg.substring(268, 279));
        if(!msg.substring(279, 291).isBlank()) ask_level4_volume = Long.parseLong(msg.substring(279, 291));
        if(!msg.substring(291, 303).isBlank()) bid_level4_volume = Long.parseLong(msg.substring(291, 303));
        if(!msg.substring(303, 315).isBlank()) ask_lp_level4_volume = Long.parseLong(msg.substring(303, 315));
        if(!msg.substring(315, 327).isBlank()) bid_lp_level4_volume = Long.parseLong(msg.substring(315, 327));

        if(!msg.substring(327, 338).isBlank()) ask_level5_price = Double.parseDouble(msg.substring(327, 338));
        if(!msg.substring(338, 349).isBlank()) bid_level5_price = Double.parseDouble(msg.substring(338, 349));
        if(!msg.substring(349, 361).isBlank()) ask_level5_volume = Long.parseLong(msg.substring(349, 361));
        if(!msg.substring(361, 373).isBlank()) bid_level5_volume = Long.parseLong(msg.substring(361, 373));
        if(!msg.substring(373, 385).isBlank()) ask_lp_level5_volume = Long.parseLong(msg.substring(373, 385));
        if(!msg.substring(385, 397).isBlank()) bid_lp_level5_volume = Long.parseLong(msg.substring(385, 397));

        if(!msg.substring(397, 408).isBlank()) ask_level6_price = Double.parseDouble(msg.substring(397, 408));
        if(!msg.substring(408, 419).isBlank()) bid_level6_price = Double.parseDouble(msg.substring(408, 419));
        if(!msg.substring(419, 431).isBlank()) ask_level6_volume = Long.parseLong(msg.substring(419, 431));
        if(!msg.substring(431, 443).isBlank()) bid_level6_volume = Long.parseLong(msg.substring(431, 443));
        if(!msg.substring(443, 455).isBlank()) ask_lp_level6_volume = Long.parseLong(msg.substring(443, 455));
        if(!msg.substring(455, 467).isBlank()) bid_lp_level6_volume = Long.parseLong(msg.substring(455, 467));

        if(!msg.substring(467, 478).isBlank()) ask_level7_price = Double.parseDouble(msg.substring(467, 478));
        if(!msg.substring(478, 489).isBlank()) bid_level7_price = Double.parseDouble(msg.substring(478, 489));
        if(!msg.substring(489, 501).isBlank()) ask_level7_volume = Long.parseLong(msg.substring(489, 501));
        if(!msg.substring(501, 513).isBlank()) bid_level7_volume = Long.parseLong(msg.substring(501, 513));
        if(!msg.substring(513, 525).isBlank()) ask_lp_level7_volume = Long.parseLong(msg.substring(513, 525));
        if(!msg.substring(525, 537).isBlank()) bid_lp_level7_volume = Long.parseLong(msg.substring(525, 537));

        if(!msg.substring(537, 548).isBlank()) ask_level8_price = Double.parseDouble(msg.substring(537, 548));
        if(!msg.substring(548, 559).isBlank()) bid_level8_price = Double.parseDouble(msg.substring(548, 559));
        if(!msg.substring(559, 571).isBlank()) ask_level8_volume = Long.parseLong(msg.substring(559, 571));
        if(!msg.substring(571, 583).isBlank()) bid_level8_volume = Long.parseLong(msg.substring(571, 583));
        if(!msg.substring(583, 595).isBlank()) ask_lp_level8_volume = Long.parseLong(msg.substring(583, 595));
        if(!msg.substring(595, 607).isBlank()) bid_lp_level8_volume = Long.parseLong(msg.substring(595, 607));

        if(!msg.substring(607, 618).isBlank()) ask_level9_price = Double.parseDouble(msg.substring(607, 618));
        if(!msg.substring(618, 629).isBlank()) bid_level9_price = Double.parseDouble(msg.substring(618, 629));
        if(!msg.substring(629, 641).isBlank()) ask_level9_volume = Long.parseLong(msg.substring(629, 641));
        if(!msg.substring(641, 653).isBlank()) bid_level9_volume = Long.parseLong(msg.substring(641, 653));
        if(!msg.substring(653, 665).isBlank()) ask_lp_level9_volume = Long.parseLong(msg.substring(653, 665));
        if(!msg.substring(665, 677).isBlank()) bid_lp_level9_volume = Long.parseLong(msg.substring(665, 677));

        if(!msg.substring(677, 688).isBlank()) ask_level10_price = Double.parseDouble(msg.substring(677, 688));
        if(!msg.substring(688, 699).isBlank()) bid_level10_price = Double.parseDouble(msg.substring(688, 699));
        if(!msg.substring(699, 711).isBlank()) ask_level10_volume = Long.parseLong(msg.substring(699, 711));
        if(!msg.substring(711, 723).isBlank()) bid_level10_volume = Long.parseLong(msg.substring(711, 723));
        if(!msg.substring(723, 735).isBlank()) ask_lp_level10_volume = Long.parseLong(msg.substring(723, 735));
        if(!msg.substring(735, 747).isBlank()) bid_lp_level10_volume = Long.parseLong(msg.substring(735, 747));

        if(!msg.substring(747, 759).isBlank()) total_ask_volume = Long.parseLong(msg.substring(747, 759));
        if(!msg.substring(759, 771).isBlank()) total_bid_volume = Long.parseLong(msg.substring(759, 771));
        if(!msg.substring(771, 782).isBlank()) estimated_tading_price = Double.parseDouble(msg.substring(771, 782));
        if(!msg.substring(782, 794).isBlank()) estimated_trading_volume = Long.parseLong(msg.substring(782, 794));

        end_keyword = msg.substring(794, 795);
        createdAt = Timestamp.from(Instant.now());
    }

    public Map<String, Object> toSocket() {
        final Map<String, Object> response = new HashMap<>();
        return response;
    }

    static public String[] insertCols() {
        return new String[] {
                "sq_data_category",
                "sq_info_category",
                "sq_message_seq_number",
                "sq_board_id",
                "sq_session_id",
                "sq_isin_code",
                "sq_a_designated_number_for_an_issue",
                "sq_processing_time_of_trading_system",
                "sq_ask_level1_price",
                "sq_bid_level1_price",
                "sq_ask_level1_volume",
                "sq_bid_level1_volume",
                "sq_lp_ask_level1_volume",
                "sq_lp_bid_level1_volume",
                "sq_ask_level2_price",
                "sq_bid_level2_price",
                "sq_ask_level2_volume",
                "sq_bid_level2_volume",
                "sq_lp_ask_level2_volume",
                "sq_lp_bid_level2_volume",
                "sq_ask_level3_price",
                "sq_bid_level3_price",
                "sq_ask_level3_volume",
                "sq_bid_level3_volume",
                "sq_lp_ask_level3_volume",
                "sq_lp_bid_level3_volume",
                "sq_ask_level4_price",
                "sq_bid_level4_price",
                "sq_ask_level4_volume",
                "sq_bid_level4_volume",
                "sq_lp_ask_level4_volume",
                "sq_lp_bid_level4_volume",
                "sq_ask_level5_price",
                "sq_bid_level5_price",
                "sq_ask_level5_volume",
                "sq_bid_level5_volume",
                "sq_lp_ask_level5_volume",
                "sq_lp_bid_level5_volume",
                "sq_ask_level6_price",
                "sq_bid_level6_price",
                "sq_ask_level6_volume",
                "sq_bid_level6_volume",
                "sq_lp_ask_level6_volume",
                "sq_lp_bid_level6_volume",
                "sq_ask_level7_price",
                "sq_bid_level7_price",
                "sq_ask_level7_volume",
                "sq_bid_level7_volume",
                "sq_lp_ask_level7_volume",
                "sq_lp_bid_level7_volume",
                "sq_ask_level8_price",
                "sq_bid_level8_price",
                "sq_ask_level8_volume",
                "sq_bid_level8_volume",
                "sq_lp_ask_level8_volume",
                "sq_lp_bid_level8_volume",
                "sq_ask_level9_price",
                "sq_bid_level9_price",
                "sq_ask_level9_volume",
                "sq_bid_level9_volume",
                "sq_lp_ask_level9_volume",
                "sq_lp_bid_level9_volume",
                "sq_ask_level10_price",
                "sq_bid_level10_price",
                "sq_ask_level10_volume",
                "sq_bid_level10_volume",
                "sq_lp_ask_level10_volume",
                "sq_lp_bid_level10_volume",
                "sq_total_ask_volume",
                "sq_total_bid_volume",
                "sq_estimated_tading_price",
                "sq_estimated_trading_volume",
                "sq_end_keyword",
                "sq_crdt",
        };
    }
}
