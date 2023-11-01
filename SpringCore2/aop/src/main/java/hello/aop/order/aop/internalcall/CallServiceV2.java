package hello.aop.order.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 지연조회를 통해 메서드 참조를 내부참조에서 외부참조로 변경하는 방법
 * ObjectProvider, ApplicationContext 를 사용해서 지연 (LAZY) 조회
 */
@Slf4j
@Component
public class CallServiceV2 {

    /**
     * 방법 2. ObjectProvider 를 통해 지연로딩을 사용하는 방법.
     */
    private final ObjectProvider<CallServiceV2> callServiceV2ObjectProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceV2ObjectProvider) {
        this.callServiceV2ObjectProvider = callServiceV2ObjectProvider;
    }

    public void external() {
        log.info("call external");
        CallServiceV2 callServiceV2 = callServiceV2ObjectProvider.getObject();
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal");
    }



//    /**
//     * 방법 1. 무식하게 ApplicationContext 를 주입받아, 프록시 객체의 대상이 되는 객체 타입의 Bean 을 꺼내서 지연로딩을 흉내내는 방법.
//     * @param applicationContext
//     */
//    private final ApplicationContext applicationContext;
//
//    public CallServiceV2(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    public void external() {
//        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
//        callServiceV2.internal();
//    }
//
//    public void internal() {
//        log.info("call internal");
//    }
}
