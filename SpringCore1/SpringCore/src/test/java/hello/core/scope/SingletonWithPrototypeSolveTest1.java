package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeSolveTest1 {
    @Scope(value = "prototype")
    static class PrototypeBean {
        private int count = 0;
        public void addCount() { count++; }
        public int getCount() { return count; }
        @PostConstruct
        public void init() { System.out.println("PrototypeBean.init " + this); }
        @PreDestroy
        public void destroy() { System.out.println("PrototypeBean.destroy" + this); }
    }

    @Scope(value = "singleton")
    static class ClientBean {

        private final ApplicationContext applicationContext;

        @Autowired
        public ClientBean(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public int logic() {
            PrototypeBean bean = applicationContext.getBean(PrototypeBean.class);
            bean.addCount();
            int count = bean.getCount();
            return count;
        }
    }

    @Test
    @DisplayName(value = "가장 간단한 방법은 싱글톤 빈이 프로토타입을 사용할때마다 스프링 컨테이너에 새로 요청하는 것이다.")
    public void singletonWithPrototypeSolveTest1() {
        ConfigurableApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean bean1 = applicationContext.getBean(ClientBean.class);
        int logic1 = bean1.logic();
        assertThat(logic1).isEqualTo(1);

        ClientBean bean2 = applicationContext.getBean(ClientBean.class);
        int logic2 = bean2.logic();
        assertThat(logic2).isEqualTo(1);
    }
}
