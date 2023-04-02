package com.example.springcore2.app.v3;

import com.example.springcore2.app.trace.TraceStatus;
import com.example.springcore2.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;

    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderServiceV2.orderItem()");

            orderRepository.save(itemId);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

}
