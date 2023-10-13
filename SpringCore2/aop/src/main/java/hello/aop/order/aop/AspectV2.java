package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {


    // @Pointcut 에 포인트컷 표현식을 사용 --> 메서드 이름과 파라미터를 합쳐서 포인트컷 시그니처 (signature) 라고 한다. --> 현재 포인트컷 시그니처는 allOrder() 임.
    // 1. 메서드의 반환 타입은 void 이여야 한다.
    // 2. 코드 내용은 비워둔다.
    // 3. @Around 어드바이스에서는 포인트컷을 직접 지정해주어도 되지만, 포인트컷 시그니처를 사용해도 된다.
    // 4. private, public 같은 접근 제어자는 내부에서만 사용하면 private 를 사용해도 되지만, 다른 애스펙트에서 참고하려면 public 을 사용해야 한다.
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("[log] {}", proceedingJoinPoint.getSignature());
        return proceedingJoinPoint.proceed();
    }
}
