package hello.aop.order.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    /**
     * 같은 프록시 대상 객체 내부에서 서로 다른 메서드를 내부적으로 호출할때는 Advise 가 적용되지 않는다. --> 따라서 internal() 에 대한 AOP 는 동작하지 않는다.
     */
    public void external() {
        log.info("call external");
        internal(); // 내부 메서드 호출 (this.internal())
    }

    public void internal() {
        log.info("call internal");
    }


}
