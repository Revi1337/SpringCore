package hello.aop.order.aop.member;

import hello.aop.order.aop.member.annotation.ClassAop;
import hello.aop.order.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component  // Aop 가 적용되려면 Bean 으로 등록되어야함.
public class MemberServiceImpl implements MemberService{

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }

}
