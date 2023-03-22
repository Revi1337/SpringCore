package hello.core.autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import static org.springframework.context.annotation.ComponentScan.*;

public class AutoWiredTest3 {

    @Component
    static class InjectedBean {
        public InjectedBean() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }
    }

    @Component
    static class BeanService {
        private InjectedBean injectedBean;

        public BeanService() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }

        @Autowired
        public BeanService(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " Required Constructor Called");
            this.injectedBean = injectedBean;
        }

        @Autowired
        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " setter Called");
            this.injectedBean = injectedBean;
        }
    }

    @Component
    static class BeanRepository {
        private InjectedBean injectedBean;

        public BeanRepository() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }

        @Autowired
        public BeanRepository(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " Required Constructor Called");
            this.injectedBean = injectedBean;
        }

        @Autowired
        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " setter Called");
            this.injectedBean = injectedBean;
        }
    }

    @Configuration
    @ComponentScan(
            excludeFilters = @Filter(
                    type = FilterType.ANNOTATION,
                    classes = Configuration.class
            )
    )
    static class BeanConfiguration2 {
    }

    @Test
    @DisplayName(value = "빈 호출순서와 초기화 순서 테스트")
    public void beanLifeCycleTest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration2.class);
        BeanConfiguration2 bean = applicationContext.getBean(BeanConfiguration2.class);
        System.out.println("bean = " + bean);
    }

}
