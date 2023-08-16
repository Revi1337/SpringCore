package hello.advanced.app.v5;

import hello.advanced.trace.callback.TraceTemplate;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @Slf4j
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        System.out.println();
        return traceTemplate.execute(
                "OrderControllerV5.request()",
                () -> {
                    orderService.orderItem(itemId);
                    return "ok";
                });
    }
}
