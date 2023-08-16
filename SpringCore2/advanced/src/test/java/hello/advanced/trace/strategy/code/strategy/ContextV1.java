package hello.advanced.trace.strategy.code.strategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Field 에 Strategy(전략) 을 보관하는 방식 (Composition 이라고도 함.)
 * ContextV1 은 변하지 않는 로직을 가지고있는 템플릿 역할을 한다. --> 일반적으로  전략 패턴에서는 이것을 Context(문맥) 이라고 한다.
 * 쉽게 이야기해서 Context(문맥) 은 크게 변하지 않지만, 그 Context(문맥) 속에서 Strategy(전략) 을 통해 일부 전략이 변경된다고 생각하면 된다.
 */
@Slf4j @RequiredArgsConstructor
public class ContextV1 {

    // 현재 Context 는 내부에 필드로 Strategy 를 갖고있다. 이 필트에 변하는 부분인 Strategy 의 구현체를 주입해주면 된다.
    // 전략 패턴의 핵심은 Context 는 Strategy 인터페이스에만 의존한다는 점이다. 덕분에 Strategy 의 구현체를 변경하거나 새로 만들어도 Context 코드에는 영향을 주지 않는다.
    // 사실 스프링에서 의존성 주입을 사용하는 방식이 바로 전략 패턴이다.

    private final Strategy strategy;

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비지니스 로직 실행
        strategy.call();            // TODO 위임.
        //비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime = {}", resultTime);
    }
}
