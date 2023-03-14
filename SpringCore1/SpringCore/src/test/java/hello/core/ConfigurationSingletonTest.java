package hello.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationSingletonTest {

    static class MemoryMemberRepository {}

    static class FixDiscountPolicy {}

    static class MemberServiceImpl {
        private final MemoryMemberRepository memoryMemberRepository;
        public MemberServiceImpl(MemoryMemberRepository memoryMemberRepository) {
            this.memoryMemberRepository = memoryMemberRepository;
        }
    }

    static class OrderServiceImpl {
        private final MemoryMemberRepository memoryMemberRepository;
        private final FixDiscountPolicy fixDiscountPolicy;
        public OrderServiceImpl(MemoryMemberRepository memoryMemberRepository, FixDiscountPolicy fixDiscountPolicy) {
            this.memoryMemberRepository = memoryMemberRepository;
            this.fixDiscountPolicy = fixDiscountPolicy;
        }
    }

    @Configuration
    static class ConfigurationClass {
        @Bean
        public MemoryMemberRepository memberRepository() {
            System.out.println("memberRepository() Called");
            return new MemoryMemberRepository();
        }
        @Bean
        public FixDiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
        @Bean
        public MemberServiceImpl memberService() {
            System.out.println("memberService() Called");
            return new MemberServiceImpl(memberRepository());
        }
        @Bean
        public OrderServiceImpl orderService() {
            System.out.println("orderService() Called");
            return new OrderServiceImpl(memberRepository(), fixDiscountPolicy());
        }
    }

    @Test
    @DisplayName(value = "@Configuration 을 붙여 SpringContainer(ApplicationContext) 에 등록해주면 의존성 주입과정에서 주입되는 Bean 들의 싱글톤까지 보장해주는 것이다.")
    public void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ConfigurationClass.class);
        MemoryMemberRepository memberRepository1 = ac.getBean("memberRepository", MemoryMemberRepository.class);
        MemoryMemberRepository memberRepository2 = ac.getBean("memberRepository", MemoryMemberRepository.class);

    // TODO 진짜 명확히 구분해서 이해해야할것은 SpringContainer 에 애플리케이션 구성정보를 넘길떄, 구성정보 class 에 @Configuration 어노테이션이 붙어있냐 없냐이다.
    //  (@Configuration X)
    //  1. 기본적으로 Spring Container(ApplicationContext) 애플리케이션 구성정보 클래스(ConfigurationClass)를 넘겨주면,
    //     구성정보도 Bean 으로 등록되고, 구성정보와 관련된 Bean 들도 SpringContainer 에 등록되게되는데, 이렇게 등록된 Bean 들은 여러번 조회해도 싱글톤을 보장해준다.
    //     하지만, 의존성 주입과정에서 주입되는 Bean 들의 싱글톤을 보장하지 않는다.
    //  (@Configuration O)
    //  2. 추가적으로 애플리케이션 구성정보 클래스에 @Configuration 을 붙여 Spring Container(ApplicationContext) 에 넘겨주면
    //     등록된 Bean 들을 조회했을때의 싱글톤을 보장해주는 것뿐만 아니라, 의존성을 주입하는 과정에서 주입되는 Bean 싱글톤까지 보장해주는 것이다.
    //     의존성을 주입하는 과정에서 주입되는 Bean 싱글톤까지 보장해주는 역할은 @Configuration 이 붙은 설정정보클래스 (ConfigurationClass) 에게 있다.
    //     더욱 정확히 말하자면 ConfigurationClass 타입의 Bean 을 조회해 타입을 보면 CGLIB 가 나오는데, CGLIB 가 이러한 역할을 해주는 것이다.

    // TODO --> 즉, 구성정보 클래스에 @Configuration 을 붙여주냐 안붙여주냐의 차이는 의존성 주입과정에서 주입되는 Bean 들의 싱글톤까지 보장하냐 안하냐의 차이다.
    }
}
