package com.quant_socket.repos;

import com.quant_socket.interfaces.DataSetter;
import com.quant_socket.models.Logs.SecOrderFilled;
import com.quant_socket.models.Logs.SeqQuote;
import org.springframework.stereotype.Repository;

@Repository
public class SeqQuoteRepo extends SG_repo<SeqQuote> {

    public boolean insert(DataSetter setter) {
        return super.insert(SeqQuote.class, setter);
    }
}
