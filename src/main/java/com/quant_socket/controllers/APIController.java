package com.quant_socket.controllers;

import com.quant_socket.models.Product;
import com.quant_socket.models.response.Response;
import com.quant_socket.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
@Slf4j
public class APIController {

    @Autowired
    private ProductService productService;

    @PostMapping("/issue/higher")
    public ResponseEntity<Response> getHigher(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("상승상위 증권을 불러왔습니다.");
        res.setData(productService.orderHigher(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/issue/lower")
    public ResponseEntity<Response> getLower(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("하락상위 증권을 불러왔습니다.");
        res.setData(productService.orderLower(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/issue/total-price")
    public ResponseEntity<Response> getHavingCount(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("시총상위 증권을 불러왔습니다.");
        res.setData(productService.orderHavingCount(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/issue/trading-count")
    public ResponseEntity<Response> getTradingCount(@RequestParam(required = false) String type) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("거래상위 증권을 불러왔습니다.");
        res.setData(productService.orderTradingCount(type));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/issue/from-isincode")
    public ResponseEntity<Response> getFromIsinCodes(@RequestParam(required = false) String[] code) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("상품을 조회했습니다.");
        if(code != null) res.setData(productService.fromIsinCodes(List.of(code)));

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/chart/minute")
    public ResponseEntity<Response> getChartFromMinute(@RequestParam(required = false) String code) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("분 차트를 조회했습니다.");
        res.setData(productService.minuteChartFromCode(code));
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/chart/day")
    public ResponseEntity<Response> getChartFromDay(@RequestParam(required = false) String code) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("일 차트를 조회했습니다.");

        final List<Map<String, Object>> list = productService.dayChartFromCode(code);
        final boolean contains = list.stream().anyMatch(map -> map.get("Date").equals(getNowMillisecond()));
        if(!contains) {
            final Product prod = productService.productFromIsinCode(code);
            if(prod != null) {
                final Map<String, Object> data = new LinkedHashMap<>();
                data.put("Date", getNowMillisecond());
                data.put("Close", prod.getCurrentPrice());
                data.put("High", prod.getHighPrice());
                data.put("Low", prod.getLowPrice());
                data.put("Open", prod.getYesterday_price());
                data.put("Volume", prod.getTodayTradingCount());
                list.add(data);
            }
        }

        res.setData(list);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/chart/week")
    public ResponseEntity<Response> getChartFromWeek(@RequestParam(required = false) String code) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("주 차트를 조회했습니다.");

        final List<Map<String, Object>> list = productService.weekChartFromCode(code);

        if(!list.isEmpty()) {
            final Product prod = productService.productFromIsinCode(code);
            final Map<String, Object> data = list.get(list.size() - 1);
            list.forEach(row -> {
                final double high_price = (double) row.get("High");
                final double low_price = (double) row.get("Low");
                final long volume = (long) row.get("Volume");

                if(row.equals(data)) {
                    row.put("Date", getNowMillisecond());
                    row.put("High", Math.max(high_price, prod.getHighPrice()));
                    row.put("Low", Math.min(low_price, prod.getLowPrice()));
                    row.put("Close", Math.min(low_price, prod.getCurrentPrice()));
                    row.put("Volume", volume+prod.getTodayTradingCount());
                }
            });
        }

        res.setData(list);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/chart/month")
    public ResponseEntity<Response> getChartFromMonth(@RequestParam(required = false) String code) {
        final Response res = new Response();
        res.setStatusCode(HttpStatus.OK);
        res.setMessage("월 차트를 조회했습니다.");

        final List<Map<String, Object>> list = productService.monthChartFromCode(code);
        if(!list.isEmpty()) {
            final Product prod = productService.productFromIsinCode(code);
            final Map<String, Object> data = list.get(list.size() - 1);
            list.forEach(row -> {
                final double high_price = (double) row.get("High");
                final double low_price = (double) row.get("Low");
                final long volume = (long) row.get("Volume");

                if(row.equals(data)) {
                    row.put("Date", getNowMillisecond());
                    row.put("High", Math.max(high_price, prod.getHighPrice()));
                    row.put("Low", Math.min(low_price, prod.getLowPrice()));
                    row.put("Close", prod.getCurrentPrice());
                    row.put("Volume", volume+prod.getTodayTradingCount());
                }
            });
        }

        res.setData(list);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    private long getNowMillisecond() {
        LocalDate now = LocalDate.now();


        // LocalDate를 LocalDateTime으로 변환
        LocalDateTime localDateTime = now.atStartOfDay();

        // LocalDateTime을 ZonedDateTime으로 변환
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        // ZonedDateTime을 Instant로 변환
        Instant instant = zonedDateTime.toInstant();

        // Instant를 밀리초로 변환
        return instant.toEpochMilli();

    }
}
