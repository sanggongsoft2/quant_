package com.quant_socket.services;

import com.quant_socket.models.Logs.SeqQuote;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SeqQuoteService extends SocketService{
    public void dataHandler(SeqQuote data) {
        sendMessage(data.toSocket());
        sendMessage(data.toSocket(), data.getIsin_code());
    }
}
