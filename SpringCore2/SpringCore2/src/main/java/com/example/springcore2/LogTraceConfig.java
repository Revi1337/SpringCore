package com.example.springcore2;

import com.example.springcore2.app.trace.logtrace.FieldLogTrace;
import com.example.springcore2.app.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }

}
