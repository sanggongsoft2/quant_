package com.quant_socket.services;
import com.quant_socket.models.Logs.EquitiesSnapshot;
import com.quant_socket.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class EquitiesSnapshotService extends SocketService<EquitiesSnapshot>{

    public void dataHandler(EquitiesSnapshot data) {
        super.addLog(data);

        final Product product = productService.productFromIsinCode(data.getIsin_code());
        if(product != null) {
            sendMessage(data.toSocket(product));
            sendMessage(data.toSocket(product), data.getIsin_code());
        }
    }
}
