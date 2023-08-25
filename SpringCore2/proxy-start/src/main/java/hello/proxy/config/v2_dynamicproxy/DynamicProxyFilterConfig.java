package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();                                  // target
        LogTraceFilterHandler logTraceFilterHandler = new LogTraceFilterHandler(orderRepositoryV1, logTrace, PATTERNS);  // 공통로직을 실행
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(                               // 프록시 생성
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                logTraceFilterHandler);

        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));                // target
        LogTraceFilterHandler logTraceFilterHandler = new LogTraceFilterHandler(orderServiceV1, logTrace, PATTERNS);     // 공통로직을 실행
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(                                     // 프록시 생성
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                logTraceFilterHandler
        );

        return proxy;
    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceFilterHandler logTraceFilterHandler = new LogTraceFilterHandler(orderControllerV1, logTrace, PATTERNS);
        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                logTraceFilterHandler
        );

        return proxy;
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
