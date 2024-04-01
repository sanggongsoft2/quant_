package com.quant_socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuantSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuantSocketApplication.class, args);
    }

}
