package hello.advanced.trace.template;


import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    public void logic1() {
        long startTime = System.currentTimeMillis();
        //비지니스 로직 실행
        log.info("비지니스 로직1 실행");
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    public void logic2() {
        long startTime = System.currentTimeMillis();
        //비지니스 로직 실행
        log.info("비지니스 로직2 실행");
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }

    @Test
    @DisplayName(value = "Template Method 패턴 적용 전")
    public void beforeApplyTemplateMethodPattern() {
        logic1();
        logic2();
    }

    @Test
    @DisplayName(value = "Template Method 패턴 적용 후")
    public void afterApplyTemplateMethodPattern() {
        AbstractTemplate template1 =  new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 =  new SubClassLogic2();
        template2.execute();
    }

}
