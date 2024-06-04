package com.quant_socket.controllers;

import com.quant_socket.models.response.Response;
import com.quant_socket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/issue")
@RestController
public class APIController {

    @Autowired
    private ProductService productService;

    @PostMapping("/higher")
    private ResponseEntity<Response> getHigher(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("상승상위 증권을 불러왔습니다.");
        res.setData(productService.orderHigher(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/lower")
    private ResponseEntity<Response> getLower(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("하락상위 증권을 불러왔습니다.");
        res.setData(productService.orderLower(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/having-count")
    private ResponseEntity<Response> getHavingCount(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("시총상위 증권을 불러왔습니다.");
        res.setData(productService.orderHavingCount(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/trading-count")
    private ResponseEntity<Response> getTradingCount(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("거래상위 증권을 불러왔습니다.");
        res.setData(productService.orderHavingCount(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
