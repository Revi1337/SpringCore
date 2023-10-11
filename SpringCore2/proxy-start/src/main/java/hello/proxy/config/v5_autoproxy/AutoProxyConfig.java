package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import hello.proxy.trace.logtrace.ThreadLocalLogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    // AnnotationAwareAspectJAutoProxyCreator 가 Bean 으로 등록된 Advisor 들을 자동으로 찾아서 프록시 필요한 곳에 자동으로 프록시를 적용해준다.
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // pointcut
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.setMappedNames("request*", "order*", "save*");;

        // advice
        LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(nameMatchMethodPointcut, logTraceAdvice);
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        // * 모든 반환타입, hello.proxy.app..  해당 패키지와 그 하위 패키지, *(..) * 는 모든 메서드 이름, (..) 는 파라미터는 상관 없음.
        // 쉽게 얘기하면 hello.proxy.app 패키지와 그 하위 패키지의 모든 메서드는 포인트컷의 매칭 대상이 된다.
        aspectJExpressionPointcut.setExpression(
                "execution(* hello.proxy.app..*(..))"
        );

        // advice
        LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut, logTraceAdvice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        // pointcut
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(
                "execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))"
        );

        // advice
        LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(aspectJExpressionPointcut, logTraceAdvice);
    }

}
