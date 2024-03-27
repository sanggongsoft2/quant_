package com.quant_socket.models.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Response {
    @Setter(AccessLevel.NONE)
    private HttpStatus status;
    private int statusCode;
    private String message;
    private String error;
    private Object data;

    public void setStatusCode(HttpStatus status) {
        this.statusCode = status.value();
        this.status = status;
    }
}
