package hello.aop.order.aop.exam.aop;


import hello.aop.order.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    /**
     * 잊지말자. @Around 메서드 매개변수에 받고 싶은 Annotation 을 설정해주고,
     * `@Around("@annoatation(어노테이션 매개변수이름)")` 을 설정해주면 매개변수로 설정한 annoataion 을 받을 수 있다.
     * @param proceedingJoinPoint
     * @param retry
     * @return
     */
    @Around("@annotation(retry)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry = {}", proceedingJoinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount < maxRetry; retryCount++) {
            try {
                log.info("[retry] try count = {}/{}", retryCount, maxRetry);
                return proceedingJoinPoint.proceed();
            } catch (Exception exception) {
                exceptionHolder = exception;
            }
        }
        throw exceptionHolder;
    }
}
