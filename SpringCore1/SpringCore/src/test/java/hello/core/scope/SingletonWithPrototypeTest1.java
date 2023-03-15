package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

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

    @Test
    @DisplayName(value = "Prototype Bean 은 조회요청마다 새로운 인스턴스를 반환해준다.")
    public void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
        prototypeBean.addCount();
        assertThat(prototypeBean.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
    }

}
