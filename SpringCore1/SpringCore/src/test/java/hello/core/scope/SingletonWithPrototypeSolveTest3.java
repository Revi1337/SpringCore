package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeSolveTest3 {
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
    static class SingletonBean {
        Provider<PrototypeBean> prototypeBeanProvider;

        @Autowired
        public SingletonBean(Provider<PrototypeBean> prototypeBeanProvider) {
            this.prototypeBeanProvider = prototypeBeanProvider;
        }

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Test
    @DisplayName(value = "프로토타입 스코프 - 싱글톤 빈과 함께 사용시 ObjectFactory 혹은 ObjectProvider 로 문제 해결")
    public void singletonWithPrototypeSolveTest3() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(SingletonBean.class, PrototypeBean.class);
        SingletonBean bean1 = applicationContext.getBean(SingletonBean.class);
        int logic = bean1.logic();
        assertThat(logic).isEqualTo(1);

        SingletonBean bean2 = applicationContext.getBean(SingletonBean.class);
        int logic2 = bean1.logic();
        assertThat(logic2).isEqualTo(1);
    }
}
