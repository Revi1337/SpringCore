package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 관심사의 분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 명확리 분리됨.
//    --> 공현기획자의 역할을 하는 것임.
//    --> 애플리케이션의 실제 동작에 필요한 구현 객체를 생성하는 일을함.
//    --> 생성한 객체 인스턴스의 참조(래퍼런스) 를 생성자를 통해서 주입(연결) 해준다.
public class AppConfig {

    // `memoryMemberRepository` 객체를 생성하고 그 참조값을 `memberServiceImpl` 을 생성하면서 생성자로 전달.
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    // 중복을 제거하고, 역할에 따른 구현이 보이도록 리팩터링.
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    private DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }

}
