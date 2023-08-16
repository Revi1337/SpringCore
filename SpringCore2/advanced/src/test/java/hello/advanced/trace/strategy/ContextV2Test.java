package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    @DisplayName(value = "전략 패턴 사용 1 --> 파라미터 사용")
    public void strategyV1() {
        ContextV2 contextV1 = new ContextV2();

        contextV1.execute(new StrategyLogic1());
        contextV1.execute(new StrategyLogic2());
    }

    @Test
    @DisplayName(value = "전략 패턴 사용 2 --> 파라미터 사용 --> 익명 내부 클래스")
    public void strategyV2() {
        ContextV2 contextV1 = new ContextV2();

        contextV1.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비니니스 로직1 실행");
            }
        });

        contextV1.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비니니스 로직2 실행");
            }
        });
    }

    @Test
    @DisplayName(value = "전략 패턴 사용 3 --> 파라미터 사용 --> 익명 내부 클래스 --> 간략화")
    public void strategyV3() {
        ContextV2 contextV1 = new ContextV2();

        contextV1.execute(() -> log.info("비니니스 로직1 실행"));

        contextV1.execute(() -> log.info("비니니스 로직2 실행"));
    }
}
