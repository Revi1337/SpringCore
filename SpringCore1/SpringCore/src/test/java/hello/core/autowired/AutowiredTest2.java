package hello.core.autowired;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class AutowiredTest2 {

    static class InjectedBean {
        public InjectedBean() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }
    }

    static class BeanService {
        private InjectedBean injectedBean;

        public BeanService() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }

        public BeanService(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " Required Constructor Called");
            this.injectedBean = injectedBean;
        }

        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " setter Called");
            this.injectedBean = injectedBean;
        }
    }

    static class BeanRepository {
        private InjectedBean injectedBean;

        public BeanRepository() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }

        public BeanRepository(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " Required Constructor Called");
            this.injectedBean = injectedBean;
        }

        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println(this.getClass().getSimpleName() + " setter Called");
            this.injectedBean = injectedBean;
        }
    }

    @Configuration
    static class BeanConfiguration {
        @Bean
        public InjectedBean injectedBean() {
            System.out.println("injectedBean() Called");
            return new InjectedBean();
        }

        @Bean
        public BeanService beanService() {
            System.out.println("beanService() Called");
            return new BeanService(injectedBean());
        }

        @Bean
        public BeanRepository beanRepository() {
            System.out.println("beanRepository() Called");
            return new BeanRepository(injectedBean());
        }
    }

    @Test
    @DisplayName(value = "빈 호출순서와 초기화 순서 테스트")
    public void beanLifeCycleTest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfiguration.class);
    }

}
