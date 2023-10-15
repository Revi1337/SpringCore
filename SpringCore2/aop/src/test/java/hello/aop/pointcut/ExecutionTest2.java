package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest2 {

    private AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
    private Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("리턴 타입 매치")
    public void typeExactMatch() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("리턴 타입 매치 - 부모 타입 매치도 가능하다 --> 다형성도 먹힌다 이말")
    public void typeMatchSuperType() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("리턴 타입 매치 - 부모 타입을 매칭했을 때는 --> 부모타입에서 선언한 메서드만 pointcut 으로 동작한다.")
    public void typeMatchInternal() throws NoSuchMethodException {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(aspectJExpressionPointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("리턴 타입 매치 - 부모 타입을 매칭했을 때는 --> 부모타입에서 선언한 메서드만 pointcut 으로 동작한다.")
    public void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        assertThat(aspectJExpressionPointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    // TODO * 는 와일드카드. 즉 아무거나 한개를 의미. .. 는 개수 상관 X (파라미터가 없어도 된다.)

    @Test
    @DisplayName("파마리터 매치 - String 타입 파라미터 매칭")
    public void argsMatch() {
        aspectJExpressionPointcut.setExpression("execution(* *(String))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파마리터 매치 - 파라미터가 없을 때")
    public void noArgsMatch() {
        aspectJExpressionPointcut.setExpression("execution(* *())");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("파마리터 매치 - 타입은 모두 허용하지만 정확히 하나의 파라미터만 허용")
    public void argsMatchStar() {
        aspectJExpressionPointcut.setExpression("execution(* *(*))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파마리터 매치 - 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
    public void argsMatchStar2() {
        aspectJExpressionPointcut.setExpression("execution(* *(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파마리터 매치 - String 타입으로 시작하고 숫자와 무관하게 모든 파라미터, 모든 타입 허용")
    public void argsMatchStar3() {
        aspectJExpressionPointcut.setExpression("execution(* *(String, ..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
