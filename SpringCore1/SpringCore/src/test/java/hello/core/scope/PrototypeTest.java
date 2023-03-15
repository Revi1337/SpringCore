package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Scope(value = "prototype") // 의존성까지 주입 후 버려버림 --> 다른 인스턴스
    static class PrototypeBean {
        @PostConstruct
        public void init() { System.out.println("================= @PostConstruct Called ================="); }
        @PreDestroy
        public void destroy() { System.out.println("================= @PreDestroy Called ================="); }
    }

    @Test
    @DisplayName(value = "Prototype 테스트 - Prototype 타입 Bean 은 스프링컨테이너가 생성될때 같이 생성되지않는다.")
    public void prototypeTest1() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
    }

    @Test
    @DisplayName(value =
            "Prototype 테스트 - Prototype 타입 Bean 은 스프링컨테이너에 Bean 을 요청해야만 그때 Bean 이 생성되고 초기화(@PostConstruct) 가 호출된다." +
            "하지만, @PostConstruct 후 컨테이너에서 관리하지 않기때문에, 스프링컨테이너가 죽을 때 @PreDestroy 가 호출되지 않는다."
    )
    public void prototypeTest2() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("[+] Find ProtoType Bean 1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("[+] Find ProtoType Bean 2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isNotSameAs(bean2);

        ac.close();
    }

    @Test
    @DisplayName(value = "따라서 클라이언트가 직접 Bean 을 Destory 시켜주어야한다.")
    public void prototypeTest3() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("[+] Find ProtoType Bean 1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("[+] Find ProtoType Bean 2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isNotSameAs(bean2);

        ac.close();

        bean1.destroy();
        bean2.destroy();
    }
}
