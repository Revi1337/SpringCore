package com.example.springcore2.app.trace;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message;

}
