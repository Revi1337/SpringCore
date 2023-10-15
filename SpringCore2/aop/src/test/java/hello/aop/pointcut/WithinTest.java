package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("within 은 Class 안의 메서드를 모두 매치할 때 사용된다.")
    void withinExact() throws NoSuchMethodException {
        pointcut.setExpression("within(hello.aop.order.aop.member.MemberServiceImpl)");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("within 은 Class 안의 메서드를 모두 매치할 때 사용된다 - 물론 * 도 사용 가능하다..")
    void withinStar() {
        pointcut.setExpression("within(hello.aop.order.aop.member.*Service*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("within 은 Class 안의 메서드를 모두 매치할 때 사용된다 - 물론 . 로 서브패키지도 매칭 가능하다.")
    void withinSubPackage() {
        pointcut.setExpression("within(hello.aop..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("""
        within 은 정확히게 타겟과 타입이 맞아야한다. 부모타입이(다형성) 먹히지 않는다는 말. --> 인터페이스를 선정하면 안된다는 말
    """)
    void withinBeCareful() {
        pointcut.setExpression("within(hello.aop.order.aop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("""
        하지만 withIn 다르게 execution 은 인터페이스로 매칭이 가능하다.
    """)
    void executionIsMatch() {
        pointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
