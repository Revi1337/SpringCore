package hello.proxy.proxyfactory;


import hello.proxy.cglib.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    public void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);       // 프록시의 호출 대상을 넘겨준다. (target)
        proxyFactory.addAdvice(new TimeAdvice());                   // 프록시 팩토리를 통해서 만든 프록시가 사용할 부가 기능 로직 설정
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        boolean isAopProxy = AopUtils.isAopProxy(proxy); // ProxyFactory 를 통해 만들어진 Proxy 객체였을때만 동작함.
        assertThat(isAopProxy).isTrue();

        boolean isJdkDynamicProxy = AopUtils.isJdkDynamicProxy(proxy); // (JDK 동적 프록시로 만들어진 프록시인지 검사 ) ProxyFactory 를 통해 만들어진 Proxy 객체였을때만 동작함.
        assertThat(isJdkDynamicProxy).isTrue();

        boolean isCglibProxy = AopUtils.isCglibProxy(proxy); // (CGLIB 로 만들어진 만들어진 프록시인지 검사 ) ProxyFactory 를 통해 만들어진 Proxy 객체였을때만 동작함.
        assertThat(isCglibProxy).isFalse();
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    public void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);       // 프록시의 호출 대상을 넘겨준다. (target)
        proxyFactory.addAdvice(new TimeAdvice());                   // 프록시 팩토리를 통해서 만든 프록시가 사용할 부가 기능 로직 설정
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.call();

        boolean isAopProxy = AopUtils.isAopProxy(proxy); // ProxyFactory 를 통해 만들어진 Proxy 객체였을때만 동작함.
        assertThat(isAopProxy).isTrue();

        boolean isJdkDynamicProxy = AopUtils.isJdkDynamicProxy(proxy); // (JDK 동적 프록시로 만들어진 프록시인지 검사 ) ProxyFactory 를 통해 만들어진 Proxy 객체였을때만 동작함.
        assertThat(isJdkDynamicProxy).isFalse();

        boolean isCglibProxy = AopUtils.isCglibProxy(proxy); // (CGLIB 로 만들어진 만들어진 프록시인지 검사 ) ProxyFactory 를 통해 만들어진 Proxy 객체였을때만 동작함.
        assertThat(isCglibProxy).isTrue();
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB 를 사용하고, 클래스 기반 프록시 사용.")
    public void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);         // TODO 이 옵션을 의미.
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass = {}", target.getClass());
        log.info("proxyClass = {}", proxy.getClass());

        proxy.save();

        boolean isAopProxy = AopUtils.isAopProxy(proxy);
        assertThat(isAopProxy).isTrue();

        boolean isJdkDynamicProxy = AopUtils.isJdkDynamicProxy(proxy);
        assertThat(isJdkDynamicProxy).isFalse();

        boolean isCglibProxy = AopUtils.isCglibProxy(proxy);
        assertThat(isCglibProxy).isTrue();
    }

}
