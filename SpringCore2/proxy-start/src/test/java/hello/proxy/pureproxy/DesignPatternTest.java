package hello.proxy.pureproxy;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ModelAttribute;


public class DesignPatternTest {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Slf4j @RequiredArgsConstructor
    static class ProxyClient {
        private final ProxyServer proxyServer;
        public void execute() {
            String result = proxyServer.run();
            log.info("ProxyClient.execute Result - {}", result);
        }
    }

    @Slf4j
    static class ProxyServer {

        public String run() {
            log.info("ProxyServer.run");
            sleep(1000);
            return "OK";
        }

        private void sleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName(value = "Proxy 패턴 적용 전 테스트")
    public void beforeProxyPatternTest() {
        ProxyServer proxyServer = new ProxyServer();
        ProxyClient proxyClient = new ProxyClient(proxyServer);

        proxyClient.execute();
        proxyClient.execute();
        proxyClient.execute();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Slf4j @RequiredArgsConstructor
    static class ProxyClient2 {

        private final Component proxy;

        public void execute() {
            String result = proxy.run();
        }
    }

    interface Component {
        String run();
    }

    @Slf4j @RequiredArgsConstructor
    static class Cache implements Component{

        private final Component targetInstance;

        private String cacheResult;

        @Override
        public String run() {
            log.info("프록시 호출");
            if (cacheResult == null) {
                cacheResult = targetInstance.run();
            }
            return cacheResult;
        }
    }

    @Slf4j
    static class ProxyServer2 implements Component{

        @Override
        public String run() {
            log.info("실제 객체 호출");
            sleep(1000);
            return "OK";
        }

        private void sleep(int millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    @DisplayName(value = "Proxy 패턴 적용 후 테스트")
    public void afterProxyPatternTest() {
        ProxyServer2 server = new ProxyServer2();
        Cache cache = new Cache(server);
        ProxyClient2 client = new ProxyClient2(cache);

        client.execute();
        client.execute();
        client.execute();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
