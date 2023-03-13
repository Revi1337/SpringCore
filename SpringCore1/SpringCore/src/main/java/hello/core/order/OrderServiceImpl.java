package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // 철저하게 DIP 를 지키고있음. (MemberRepository, DiscountPolicy 인터페이스에만 의존함. 구체적인 클래스에 전혀 의존하지 않음)
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired // MemberRepository 타입과 DiscountPolicy 타입의 Bean 을 의존관계를 자동으로 주입해줌. (생성자 주입)
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

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
