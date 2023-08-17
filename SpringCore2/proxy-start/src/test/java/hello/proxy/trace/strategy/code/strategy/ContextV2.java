package hello.proxy.trace.strategy.code.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Field 에 Strategy(전략) 을 보관하는 방식 (전략을 파라미터로 전달받는 방식)
 */
@Slf4j @RequiredArgsConstructor
public class ContextV2 {

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
        //비지니스 로직 실행
        strategy.call();            // TODO 위임.
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }
}
