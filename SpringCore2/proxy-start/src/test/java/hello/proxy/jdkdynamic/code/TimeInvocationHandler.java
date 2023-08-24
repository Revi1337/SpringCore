package hello.proxy.jdkdynamic.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// TODO 1. JDK 동적 프록시가 제공하는 InvocationHandler 를 구현해야 JDK 동적 프록시에 적용할 공통 로직을 개발할 수 있다. (약속)
@Slf4j @RequiredArgsConstructor
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target; // TODO 2. 프록시 객체는 항상 호출할 대상객체가 있어야한다.

    @Override       // TODO Object : 프록시 자신, Method 호출한 메서드, Object[] 메서드를 호출할때 전달한 인수
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = method.invoke(target, args);    // TODO 3. Reflection 을 사용해서 target 인스턴스의 메서드를 실행. (메서드를 호출하는 부분이 동적이다.)

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("TimeProxy 종료 resultTime = {}", resultTime);
        return result;
    }
}
