package hello.advanced;

import hello.advanced.trace.logtrace.FieldLogTrace;
import hello.advanced.trace.logtrace.LogTrace;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @PostConstruct
    public void init() {
        System.out.println("LogTraceConfig.init");
    }

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
