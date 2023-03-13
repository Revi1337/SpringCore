package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;            // 모든 DiscountPolicy 타입의 Bean 을 Map 으로 받을 수도있고
        private final List<DiscountPolicy> policies;                    // List 로도 받을 수 있다.

        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {    //
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }

    @Test
    @DisplayName(value = "모든 Bean 혹은 특정 타입의 Bean 을 모두 출력")
    public void findAllBean() {
        // DiscountService 와 AutoAppConfig 를 스프링 컨테이너에 등록. --> 결국은 둘다 Bean 으로 등록됨.
        ApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);
    }

    @Test
    @DisplayName(value = "DiscountService 타입의 Bean 을 모두 뽑은 다음, 할인코드(discountCode) 에 맞는 Bean 을 사용")
    public void findAllBean2() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);

        String discountCode = "fixDiscountPolicy";
//        String discountCode = "rateDiscountPolicy";

        if (discountCode.equals("rateDiscountPolicy")) {
            int discountPrice = discountService.discount(member, 20000, discountCode);
            assertThat(discountService).isInstanceOf(DiscountService.class);
            assertThat(discountPrice).isEqualTo(2000);
        } else if (discountCode.equals("fixDiscountPolicy")) {
            int discountPrice = discountService.discount(member, 20000, discountCode);
            assertThat(discountService).isInstanceOf(DiscountService.class);
            assertThat(discountPrice).isEqualTo(1000);
        }
    }

}
