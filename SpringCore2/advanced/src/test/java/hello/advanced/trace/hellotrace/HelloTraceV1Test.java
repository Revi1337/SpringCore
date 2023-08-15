package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV1Test {

    @Test
    public void begin_end() {
        HelloTraceV1 traceV1 = new HelloTraceV1();
        traceV1.begin("hello");
    }

    @Test
    public void begin_exception() {
        HelloTraceV1 helloTraceV1 = new HelloTraceV1();
        TraceStatus status = helloTraceV1.begin("hello");
        helloTraceV1.exception(status, new IllegalStateException());
    }

}