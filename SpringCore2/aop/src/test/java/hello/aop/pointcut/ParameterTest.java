package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.annotation.ClassAop;
import hello.aop.order.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired MemberService memberService;

    @Test
    public void success() {
        log.info("memberService Proxy = {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* hello.aop.order.aop.member..*.*(..))")
        private void allMember() {}

        // args 사용전
        @Around("allMember()")
        public Object logArgs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            Object arg1 = proceedingJoinPoint.getArgs()[0];
            log.info("[logArgs1] {}, arg = {}", proceedingJoinPoint.getSignature(), arg1);
            return proceedingJoinPoint.proceed();
        }

        // args 사용 후 1
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint proceedingJoinPoint, Object arg) throws Throwable {
            log.info("[logArgs2] {}, arg = {}", proceedingJoinPoint.getSignature(), arg);
            return proceedingJoinPoint.proceed();
        }

        // args 사용 후 2
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg){
            log.info("[logArgs3] arg = {}", arg);
        }

        ///////////////////////// this 와 target 차이 /////////////////////////

        // this --> Proxy 객체를 의미. CGLIB 혹은 JDk 동적 프록시
        @Before("allMember() && this(obj)")
        public void thisArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[this {}] arg = {}", joinPoint.getSignature(), obj.getClass());
        }
        // target --> Proxy 가 호출할 실제 대상
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj) {
            log.info("[target {}] arg = {}", joinPoint.getSignature(), obj.getClass());
        }

        ///////////////////////// @target & @within /////////////////////////

        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@target {}] arg = {}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation) {
            log.info("[@within {}] arg = {}", joinPoint.getSignature(), annotation);
        }

        ///////////////////////// @annotation /////////////////////////

        // 메서드의 어노테이션을 전달받는다.
        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) {
            log.info("[@annotation {}] annotationValue = {}", joinPoint.getSignature(), annotation.value());
        }
    }

}
