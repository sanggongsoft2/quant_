package com.quant_socket.services;

import com.quant_socket.models.Logs.SeqQuote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeqQuoteService extends SocketService<SeqQuote>{
    public void dataHandler(SeqQuote data) {
        super.addLog(data);

        sendMessage(data.toSocket());
        sendMessage(data.toSocket(), data.getIsin_code());
    }
}
