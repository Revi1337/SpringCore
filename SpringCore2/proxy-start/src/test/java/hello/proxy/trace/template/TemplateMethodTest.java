package hello.proxy.trace.template;


import hello.proxy.trace.template.code.AbstractTemplate;
import hello.proxy.trace.template.code.SubClassLogic1;
import hello.proxy.trace.template.code.SubClassLogic2;
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
    @DisplayName(value = "Template Method 패턴 적용 후 --> 단점은 서브클래스를 계속 만들어내야한다는것 --> 익명 내부클래스를 사용하여 단점을 커버 가능하다.")
    public void afterApplyTemplateMethodPattern() {
        AbstractTemplate template1 =  new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 =  new SubClassLogic2();
        template2.execute();
    }

    @Test
    @DisplayName(value = "Template Method 패턴 적용 후 --> 익명 내부클래스를 사용하여 서브클래스를 계속 만들어야하는 단점을 극복.")
    public void afterApplyTemplateMethodPattern2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직 1 실행");
            }
        };
        log.info("Class Name1 = {}", template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직 2 실행");
            }
        };
        log.info("Class Name2 = {}", template2.getClass());
        template2.execute();
    }

}
