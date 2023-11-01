package hello.aop.proxyvs;

import hello.aop.order.aop.member.MemberService;
import hello.aop.order.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ProxyCastingTest {

    @Test
    public void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false);    // JDK 동적 프록시

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class = {}", memberServiceProxy.getClass());

        // JDK 동적 프록시를 구현 클래스로 캐스팅 시도 실패. ClassCastException (JDK 동적 프록시는 MemverService 구현한것이지. MemberServiceImpl 은 뭔지도 모름)
        assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    public void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);    // CGLIB 프록시 생성

        // 프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        log.info("proxy class = {}", memberServiceProxy.getClass());

        // CGLIB 프록시를 구현 클래스로 캐스팅 성공.
        // 왜냐하면 CGLIB 프록시는 프록시 대상 객체 그 자체 (여기서는 MemverServiceImpl 구현체) 를 상속받아 프록시를 생성한것이기 때문에 가능하다.
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
    }

}
