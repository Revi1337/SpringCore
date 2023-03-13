package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {
    // 잊지말것 --> @Configuration 을 붙이지지 않아도 Bean 으로 등록된다. --> 단지, 싱글톤만 보장되지 않을뿐임.
    static class TestBean {

        @Autowired(required = false)          // required = false 로설정되어있을 경우, 주입해줄 Bean(Member) 이 없으면 setNoBean1 메서드 자체가 호출이 되지 않음.
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
            // Member 는 Bean 이 아니기 때문에 setNoBean1() 는 호출자체가 되지 않는다.
        }

        @Autowired
        public void setNoBean2(@Nullable Member noBean1) {  // @Nullable 로 설정되어 있을 경우, 주입해줄 Bean(Member) 이 없으면 null 이 들어옴
            System.out.println("noBean2 = " + noBean1);
        }

        @Autowired
        public void setNoBean3(Optional<Member> noBean1) { // Optional<> 로 설정되어있을경우, 주입해줄 Bean(Member) 이 없으면 Optional.empty 로 감싸줌
            System.out.println("noBean3 = " + noBean1);
        }

    }

    @Test
    public void autowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class); // 스프링 컨테이너를 생성할 때 애플리케이션 구성 정보를 지정해준다.
        TestBean bean = ac.getBean(TestBean.class);

    }
}
