package com.quant_socket.services;

import com.quant_socket.models.Signal;
import com.quant_socket.repos.SignalRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignalService {

    @Autowired
    private SignalRepo repo;

    @Getter
    private Signal signal;

    public void refresh() {
        signal = repo.findLastestSignal();
    }
}
