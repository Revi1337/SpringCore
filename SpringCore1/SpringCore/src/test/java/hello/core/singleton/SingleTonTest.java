package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleTonTest {

    @Test
    @DisplayName(value = "스프링 없는 순수한 DI 컨테이너 의 문제점 - 요청을 할 때마다 객체를 새로 생성")
    public void pureContainer() {
        AppConfig appConfig = new AppConfig();

        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인 (요청이 들어올떄마다 객체가 생김 --> 사용자가 많으면 초당 객체가 20000 개이상씩 생길수가 있음.)
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService1 = " + memberService1);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2).as("참조값이 다르다");

        // 결론 : 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. --> 싱글톤 패턴.
    }

    @Test
    @DisplayName(value = "싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest() {
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        // isSameAs ==
        // isEqualTo equals()
        assertThat(instance1).isSameAs(instance2);
    }
}
