package hello.proxy.pureproxy.decorator.code;


import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;

@Slf4j
public class TimeDecorator implements Component{

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");

        // TODO 부가적인 기능 추가
        Instant startTime = Instant.now();
        component.operation();
        Instant endTime = Instant.now();

        String totalTime = Duration.between(startTime, endTime).toString();
        log.info("TimeDecorator totalTime = {}", totalTime);
        return totalTime;
    }
}

