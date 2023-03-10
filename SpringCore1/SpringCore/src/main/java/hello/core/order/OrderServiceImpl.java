package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    // 철저하게 DIP 를 지키고있음. (MemberRepository, DiscountPolicy 인터페이스에만 의존함. 구체적인 클래스에 전혀 의존하지 않음)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // OrderServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다 (생성자 주입패턴)
    // OrderServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부(AppConfig)에서 결정된다.
    // OrderServiceImpl 은 이제부터 의존관계에 대한 고민은 외부(AppConfig)에 맡기고 실행에만 집중하면 된다.
    // OrderServiceImpl 입장에서 보면 의존관계를 외부(AppConfig) 에서 주입해주는 것 같다고 해서, DI(Dependency Injection) 우리말로 의존관계 주입 또는 의존성 주입이라고 한다.
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

}
