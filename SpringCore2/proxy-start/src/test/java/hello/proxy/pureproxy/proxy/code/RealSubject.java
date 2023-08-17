package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

// TODO Proxy Pattern : 프록시를 사용하는 여러패턴중 하나일 뿐이며 이름에 프록시가 들어갔을 뿐이다. (접근제어가 목적)
@Slf4j
public class RealSubject implements Subject{

    @Override
    public String operation() {
        log.info("실제 객체 호출");
        sleep(1000);
        return "data";
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
             e.printStackTrace();
        }
    }
}
