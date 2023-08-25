package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();                                  // target
        LogTraceBasicHandler logTraceBasicHandler = new LogTraceBasicHandler(orderRepositoryV1, logTrace);  // 공통로직을 실행
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(                               // 프록시 생성
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                logTraceBasicHandler);

        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));                // target
        LogTraceBasicHandler logTraceBasicHandler = new LogTraceBasicHandler(orderServiceV1, logTrace);     // 공통로직을 실행
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(                                     // 프록시 생성
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                logTraceBasicHandler
        );

        return proxy;
    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceBasicHandler logTraceBasicHandler = new LogTraceBasicHandler(orderControllerV1, logTrace);
        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                logTraceBasicHandler
        );

        return proxy;
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
