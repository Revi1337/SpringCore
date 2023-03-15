package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest2 {

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
        private final PrototypeBean prototypeBean;

        @Autowired                                          // ClientBean 이 생성되고, Prototype Bean 을 주입받게됨.
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Test
    @DisplayName(
            value = "하지만, Singleton Bean 안에서 Prototype Bean 의 의존성을 주입받으면 Singleton 처럼 동작한다." +
                    "Singleton Bean 이 내부에 가지고있는 Prototype Bean 은 이미 과거에 의존성 주입이 끝난 빈이기 때문이다." +
                    "따라서 Singleton Bean 의 의존성 주입시점에 스프링컨테이너에 요청해서 Prototype Bean 이 생성되어 주입된것이지 사용할 때마다 새로 생성되는 것이 아니다."
    )
    public void prototypeFind() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        // ClientBean 에서 주입받은 Prototype 빈은 이미 의존성주입이 끝난상태.
        ClientBean clientBean1 = applicationContext.getBean(ClientBean.class);
        int logic1 = clientBean1.logic();
        assertThat(logic1).isEqualTo(1);

        ClientBean clientBean2 = applicationContext.getBean(ClientBean.class);
        int logic2 = clientBean2.logic();
        assertThat(logic2).isEqualTo(2);
    }

}
