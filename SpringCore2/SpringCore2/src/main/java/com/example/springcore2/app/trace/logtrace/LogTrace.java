package com.example.springcore2.app.trace.logtrace;

import com.example.springcore2.app.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception exception);

}
