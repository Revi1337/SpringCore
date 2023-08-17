package hello.proxy.trace.hellotrace;

import hello.proxy.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {

    @Test
    public void begin_end() {
        HelloTraceV2 traceV2 = new HelloTraceV2();
        TraceStatus status1 = traceV2.begin("hello1");
        TraceStatus status2 = traceV2.beginSync(status1.getTraceId(), "hello2");
        traceV2.end(status2);
        traceV2.end(status1);
    }

    @Test
    public void begin_exception() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }
}
