package hello.aop.order.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    /**
     * 1. setter 를 통해 자기자신을 의존관계 주입을 해준다.
     *         --> 만약, 생성자주입으로 자기자신을 주입받게되면 오류가 뜸. 왜냐? 자기자신을 주입받기때문에 순환참조가 발생함.
     *         --> 따라서 setter 를 통해 의존관계 주입을 해준다.
     *             --> 하지만..스프링 부트 2.6 릴리즈 노트를 확인해보니 순환 참조를 기본적으로 금지하도록 변경되었다.
     *                 따라서 순환 참조를 허용하도록 해결하려면 application.properties 파일에 다음을 추가해야 한다.
     *                 `spring.main.allow-circular-references=true`
     * @param callServiceV1
     */
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 setter = {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    /**
     * 2. 그리고 기존 내부메서드 참조를 주입받은 프록시객체의 메서드 외부참조로 바꾸어준다. (내부참조 --> 외부참조로 변경됨.)
     */
    public void external() {
        log.info("call external");
        callServiceV1.internal();
    }

    public void internal() {
        log.info("call internal");
    }

}
