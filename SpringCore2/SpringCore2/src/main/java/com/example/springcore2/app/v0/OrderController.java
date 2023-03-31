package com.example.springcore2.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor
public class OrderController {

    private final OrderServiceV0 orderServiceV0;

    @GetMapping(value = "/v0/request")
    public String request(String itemId) {
        orderServiceV0.orderItem(itemId);
        return "ok";
    }

}
