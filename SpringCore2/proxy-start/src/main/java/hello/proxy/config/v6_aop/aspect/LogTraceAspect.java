package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // TODO 즉, Pointcut + Advisor = Advisor
    @Around("execution(* hello.proxy.app..*(..))")      // 1. Pointcut
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 2. Advisor
        TraceStatus status = null;
        try {
            String message = proceedingJoinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // target 호출
            Object result = proceedingJoinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

}
