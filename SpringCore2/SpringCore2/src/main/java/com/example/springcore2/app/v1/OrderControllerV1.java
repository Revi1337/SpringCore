package com.example.springcore2.app.v1;

import com.example.springcore2.app.trace.TraceStatus;
import com.example.springcore2.app.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;

    private final HelloTraceV1 trace;

    @GetMapping(value = "/v1/request")
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV1.request()");

            orderService.orderItem(itemId);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        } finally {
            return "ok";
        }
    }

}
