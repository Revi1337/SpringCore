package hello.core.web;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[ " + uuid + " ]" + "[ " + requestURL + " ] " + message);
    }

    @PostConstruct
    public void init() {
        // 이 빈이 생성되는 시점에 자동으로 @PostConstruct 초기화 메서드를 사용해서 uuid 를 생성해서 저장해둔다.
        // 이 빈은 HTTP 요청 당 하나씩 생성되므로, uuid 를 저장해두면 다른 HTTP 요청과 구분할 수 있다.
        uuid = UUID.randomUUID().toString();
        System.out.println("[ " + uuid + " ] request scope Bean Create : " + this);
    }

    @PreDestroy
    public void close() {
        // 이 빈이 소멸되는 시점에 @PreDestroy 를 사용해서 종료 메시지를 남긴다
        System.out.println("[ " + uuid + " ] request scope Bean Close : " + this);
    }

}
