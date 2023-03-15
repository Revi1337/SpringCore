package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Scope(value = "singleton")     // 디폴트가 singleton 이라 써주지않아도됨. 하지만, 예제니까 써준것임.
    static class SingletonBean {
        @PostConstruct
        public void init() {
            System.out.println("================= @PostConstruct Called =================");
        }
        @PreDestroy
        public void destroy() {
            System.out.println("================= @PreDestroy Called =================");
        }
    }

    @Test
    @DisplayName(value = "Singleton 테스트 - Singleton 타입 Bean 은 스프링컨테이너가 생성될때 같이 생성된다.")
    public void singletonBeanFind1() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
    }

    @Test
    @DisplayName(value = "Singleton 테스트 - @PostConstruct, @PreDestroy 모두 호출")
    public void singletonBeanFind2() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);
        System.out.println("bean1 = " + bean1);
        System.out.println("bean2 = " + bean2);

        assertThat(bean1).isSameAs(bean2);

        ac.close();
    }
}

