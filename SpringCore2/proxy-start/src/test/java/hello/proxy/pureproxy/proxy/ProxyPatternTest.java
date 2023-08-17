package hello.proxy.pureproxy.proxy;

import hello.proxy.pureproxy.proxy.code.CacheProxy;
import hello.proxy.pureproxy.proxy.code.ProxyPatternClient;
import hello.proxy.pureproxy.proxy.code.RealSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


// TODO Proxy Pattern : 프록시를 사용하는 여러패턴중 하나일 뿐이며 이름에 프록시가 들어갔을 뿐이다. (접근제어가 목적)
public class ProxyPatternTest {

    @Test
    @DisplayName(value = "Proxy Pattern 사용 전")
    public void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute();
        client.execute();
    }

    @Test
    @DisplayName(value = "Proxy Pattern 사용 후 --> 캐시 기능 구현 (캐시도 접근제어 기능중 하나이다)")
    public void cacheProxyTest() {
        RealSubject realSubject = new RealSubject();
        CacheProxy cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient proxyPatternClient = new ProxyPatternClient(cacheProxy);

        proxyPatternClient.execute();
        proxyPatternClient.execute();
        proxyPatternClient.execute();
    }
}
