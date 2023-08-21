package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    @DisplayName(value = "Reflection 적용 전")
    public void reflection0() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();        // 호출하는 메서드만 다름
        log.info("result = {}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB();        // 호출하는 메서드만 다름
        log.info("result = {}", result2);
        // 공통 로직2 종료
    }

    @Test
    @DisplayName(value = "Reflection 적용. 클래스 정보만 간단하게 획득")
    public void reflection1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);// --> target 인스턴스의 callA 메서드를 호출하는 것.
        log.info("result1 = {}", result1);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);// --> target 인스턴스의 callA 메서드를 호출하는 것.
        log.info("result1 = {}", result2);
    }

    @Test
    @DisplayName(value = "Reflection 적용. Reflection 으로 메서드호출을 추상화")
    public void reflection2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();

        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    private void dynamicCall(Method method, Object target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }


    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}


