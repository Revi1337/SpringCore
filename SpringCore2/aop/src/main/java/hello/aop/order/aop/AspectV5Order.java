package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    // Aspect 의 순서는 Class 단위로 실행되기때문에, 하나의 Aspect 에 여러 @Around 를 넣으면 순서가 보장되지 않음. (메서드단위에 @Order 를 써도 마찬가지)
    // 따라서 하나의 @Around 를 하나의 클래스로 빼주고, @ASpect 를 달아준 후, 클래스 단위에 @Order 를 명시해야 순서가 지정된다.

    @Aspect
    @Order(2)
    public static class LogAspect {
        // 외부의 Pointcut 을 사용할때는 패키지명도 갖고와야 한다.
        @Around("hello.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[log] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {
        // 외부의 Pointcut 을 사용할때는 패키지명도 갖고와야 한다.
        @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            try {
                log.info("[트랜잭션 시작] {}", proceedingJoinPoint.getSignature());
                Object result = proceedingJoinPoint.proceed();
                log.info("[트랜잭션 커밋] {}", proceedingJoinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[트랜잭션 롤백] {}", proceedingJoinPoint.getSignature());
                throw e;
            } finally {
                log.info("[리로스 릴리즈] {}", proceedingJoinPoint.getSignature());
            }
        }
    }

}
