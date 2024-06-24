package com.quant_socket.repos;

import com.quant_socket.models.Logs.SecuritiesQuote;
import org.springframework.stereotype.Repository;

@Repository
public class SecuritiesQuoteRepo extends SG_repo<SecuritiesQuote>{

    public void deleteBefore1Day() {
        final String sql = """
                DELETE FROM securities_quote
                WHERE DATE_SUB(CURDATE(), INTERVAL 1 DAY) > sq_crdt
                """;
        jt.update(sql);
    }
}
