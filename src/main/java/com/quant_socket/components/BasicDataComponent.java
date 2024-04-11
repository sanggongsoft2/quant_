package com.quant_socket.components;

import com.quant_socket.services.SocketLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BasicDataComponent implements ApplicationRunner {
    @Autowired
    private SocketLogService socketLogService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        socketLogService.refreshTestLogs();
    }
}
