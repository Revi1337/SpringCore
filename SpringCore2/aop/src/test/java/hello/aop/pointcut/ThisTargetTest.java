package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * spring.aop.proxy-target-class=true --> CGLIB 로 Proxy 를 생성 (디폴트)
 * spring.aop.proxy-target-class=false --> JDK 동적 프록시를 Proxy 를 생성
 */
@Slf4j
@SpringBootTest(properties = "spring.aop.proxy-target-class=true") // false, true 변경해보며 확인해볼 것.
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        ////////////////////////// INTERFACE 대상 //////////////////////////

        /**
         * this() 는 부모 타입까지 Proxy 로 생성
         * @param proceedingJoinPoint
         * @return
         * @throws Throwable
         */
        @Around("this(hello.aop.order.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[this-interface] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }

        /**
         * target() 은 부모타입은 Proxy 로 만들지 않는다.
         * @param proceedingJoinPoint
         * @return
         * @throws Throwable
         */
        @Around("target(hello.aop.order.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[target-interface] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }

        ////////////////////////// IMPL 대상 //////////////////////////

        @Around("this(hello.aop.order.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[this-impl] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }

        @Around("target(hello.aop.order.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("[target-impl] {}", proceedingJoinPoint.getSignature());
            return proceedingJoinPoint.proceed();
        }
    }
}
