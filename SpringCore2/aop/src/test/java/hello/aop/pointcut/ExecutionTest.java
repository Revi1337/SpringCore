package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

    // Pointcut 표현식을 처리해주는 클래스. 여기에 포인트컷 표현식을 지정하면 된다. (상위에 Pointcut 인터페이스를 구현하고있음.)
    private AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
    private Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("execution 으로 시작하는 표현식은 Reflection 의 Method 정보와 매칭해서 Pointcut 대상을 찾아낸다.")
    public void printMethod() {
        // execution("* ..package..Class.")
        // public java.lang.String hello.aop.order.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod = {}", helloMethod);
    }
}
