package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

    @Test
    @DisplayName(value = "TemplateCallback 테스트 --> 익명 내부 클래스")
    public void templateCallbackTest1() {
        TimeLogTemplate timeLogTemplate = new TimeLogTemplate();

        timeLogTemplate.execute(() -> log.info("비지니스 로직1 실행"));
    }
}
