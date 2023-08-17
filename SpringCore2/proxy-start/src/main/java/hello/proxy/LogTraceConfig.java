package hello.proxy;

import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @PostConstruct
    public void init() {
        System.out.println("LogTraceConfig.init");
    }

//    @Bean
//    public LogTrace logTrace() {
//        return new FieldLogTrace();
//    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
