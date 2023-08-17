package hello.proxy.trace.hellotrace;

import hello.proxy.trace.TraceStatus;
import org.junit.jupiter.api.Test;

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