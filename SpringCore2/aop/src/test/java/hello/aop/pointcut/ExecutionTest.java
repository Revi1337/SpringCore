package hello.aop.pointcut;

import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatReflectiveOperationException;

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

    @Test
    @DisplayName("가장 정확한 Pointcut")
    public void exactMatch() {
        aspectJExpressionPointcut.setExpression("execution(public String hello.aop.order.aop.member.MemberServiceImpl.hello(String))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("가장 많이 생략한 Pointcut - 반환타입 *, 메서드이름 *, 파라미터 (..)")
    public void allMatch() {
        aspectJExpressionPointcut.setExpression("execution(* *(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("이름 매칭 Pointcut - hello 메서드만")
    public void nameMatch() {
        aspectJExpressionPointcut.setExpression("execution(* hello(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패턴 이름 매칭 Pointcut - hel 로 시작하는 메서드만")
    public void nameMatchStart1() {
        aspectJExpressionPointcut.setExpression("execution(* hel*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패턴 이름 매칭 Pointcut - el 이라는 단어가 들어간 메서드만")
    public void nameMatchStart2() {
        aspectJExpressionPointcut.setExpression("execution(* *el*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("매칭 실패")
    public void nameMatchFalse() {
        aspectJExpressionPointcut.setExpression("execution(* *nono*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("패키지명 패턴 매칭")
    public void packageExactMatch1() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.member.MemberServiceImpl.hello(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("패키지명 패턴 매칭 - * 을 넣을 수 있다.")
    public void packageExactMatch2() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.member.*.*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO 패키지 매치에서 . 와 .. 의 차이를 명확하게 이해해야 한다.
    //  . : 정확하게 해다 위치의 패키지
    //  .. : 해당 위치의 패키지와 그 하위 패키지도 포함

    @Test
    @DisplayName("패키지명 패턴 매치 실패케이스 (조심해야함) - 서브패키지까지는 포함되지 않음.")
    public void packageExactMatchFail() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop.*.*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
    @Test
    @DisplayName("패키지명 패턴 매치 성공케이스 (조심해야함) - . 를 추가하여 하위 패키지까지 포함하도록 설정")
    public void packageMatchSubPackage1() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order.aop..*.*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
    @Test
    @DisplayName("패키지명 패턴 매치 성공케이스 (조심해야함) - . 를 추가하여 하위 패키지까지 포함하도록 설정")
    public void packageMatchSubPackage2() {
        aspectJExpressionPointcut.setExpression("execution(* hello.aop.order..*.*(..))");
        assertThat(aspectJExpressionPointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
