package hello.aop.order.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 내부 호출이 발생하지 않도록 구조 자체를 변경하는 방법
 */
@Slf4j
@Component
public class CallServiceV3 {

    private final InternalService internalService;

    public CallServiceV3(InternalService internalService) {
        this.internalService = internalService;
    }

    public void external() {
        log.info("call external");
        internalService.internal(); // 외부 메서드 호출.
    }

}
