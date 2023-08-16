package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

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
    @DisplayName(value = "Strategy 패턴 적용 전")
    public void beforeApplyStrategyPattern() {
        logic1();
        logic2();
    }

    @Test
    @DisplayName(value = "Strategy 패턴 적용 후")
    public void afterApplyStrategyPattern() {
        StrategyLogic1 logic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(logic1);
        context1.execute();

        StrategyLogic2 logic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(logic2);
        context2.execute();
    }

    @Test
    @DisplayName(value = "Strategy 패턴 - 익명 내부 클래스")
    public void afterApplyStrategyPattern2() {
        Strategy strategy1 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();

        Strategy strategy2 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직2 실행");
            }
        };
        ContextV1 context2 = new ContextV1(strategy2);
        context2.execute();
    }

    @Test
    @DisplayName(value = "Strategy 패턴 - 익명 내부 클래스 --> 간략화")
    public void afterApplyStrategyPattern3() {
        ContextV1 context1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        });
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직2 실행");
            }
        });
        context2.execute();
    }

    @Test
    @DisplayName(value = "Strategy 패턴 - 익명 내부 클래스 --> 간략화 --> 간략화")
    public void afterApplyStrategyPattern4() {
        ContextV1 context1 = new ContextV1(() -> log.info("비지니스 로직1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비지니스 로직2 실행"));
        context2.execute();
    }

}
