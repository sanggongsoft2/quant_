package com.quant_socket.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
@Slf4j
@RequiredArgsConstructor
public class EquitiesSnapshotService {
    private final ObjectMapper mapper;
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    public void addSession(WebSocketSession ws) {
        sessions.add(ws);
    }

    public void removeSession(WebSocketSession ws) {
        sessions.remove(ws);
    }

    public <T> void sendMessage(T message){
        sessions.removeIf(ws -> {
            if(ws.isOpen()) {
                try {
                    ws.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return false;
            } else {
                return true;
            }
        });
    }
}
