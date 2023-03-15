package hello.core;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    // =================================================================================================================================

    static class InjectedBean {
        public InjectedBean() {
            System.out.println("2. " + this.getClass().getSimpleName() + " No Args Constructor Called");
        }
    }

    static class DummyBean implements InitializingBean, DisposableBean {
        private InjectedBean injectedBean;
        public DummyBean() {
            System.out.println(this.getClass().getSimpleName() + " No Args Constructor Called");
        }
        public DummyBean(InjectedBean injectedBean) {
            System.out.println("4. " + this.getClass().getSimpleName() + " Req Args Constructor Called");
            this.injectedBean = injectedBean;
        }
        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println("5. " + this.getClass().getSimpleName() + " Setter Called");
            this.injectedBean = injectedBean;
        }
        @Override
        public void destroy() throws Exception {
            System.out.println("7. Destroy Bean");
        }
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("6. Initialize Bean");
        }
    }

    @Configuration
    static class ConfigBean {
        @Bean
        public InjectedBean injectedBean() {
            System.out.println("1. injectedBean() Called");
            return new InjectedBean();
        }
        @Bean
        public DummyBean dummyBean() {
            System.out.println("3. dummyBean() Called");
            DummyBean dummyBean = new DummyBean(injectedBean());
            dummyBean.setInjectedBean(injectedBean());
            return dummyBean;
        }
    }

    @Test
    @DisplayName(value = "1. Bean LifeCycle Callback [ InitializingBean, DisposableBean ]")
    public void lifeCycleTest1() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigBean.class);
        applicationContext.close();
    }

    // =================================================================================================================================

    static class DummyBean2 {
        private InjectedBean injectedBean;

        public DummyBean2(InjectedBean injectedBean) {
            System.out.println("4. " + this.getClass().getSimpleName() + " Req Args Constructor Called");
            this.injectedBean = injectedBean;
        }

        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println("5. " + this.getClass().getSimpleName() + " Setter Called");
            this.injectedBean = injectedBean;
        }

        public void init() {
            System.out.println("6. Initialize Bean");
        }

        public void close() {
            System.out.println("7. Destroy Bean");
        }
    }

    @Configuration
    static class ConfigBean2 {
        @Bean
        public InjectedBean injectedBean() {
            System.out.println("1. injectedBean() Called");
            return new InjectedBean();
        }
        @Bean(initMethod = "init", destroyMethod = "close")
        public DummyBean2 dummyBean2() {
            DummyBean2 dummyBean2 = new DummyBean2(injectedBean());
            dummyBean2.setInjectedBean(injectedBean());
            return dummyBean2;
        }
    }

    @Test
    @DisplayName(value = "2. Bean LifeCycle Callback [ @Bean Attribute ]")
    public void lifeCycleTest2() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigBean2.class);
        applicationContext.close();
    }

    // =================================================================================================================================

    static class DummyBean3 {

        private InjectedBean injectedBean;

        public DummyBean3(InjectedBean injectedBean) {
            System.out.println("4. " + this.getClass().getSimpleName() + " Req Args Constructor Called");
            this.injectedBean = injectedBean;
        }

        public void setInjectedBean(InjectedBean injectedBean) {
            System.out.println("5. " + this.getClass().getSimpleName() + " Setter Called");
            this.injectedBean = injectedBean;
        }

        @PostConstruct
        public void postConstruct() {
            System.out.println("6. Initializing Bean!!!!!!");
        }

        @PreDestroy
        public void preDestroy() {
            System.out.println("7. Destroying Bean!!!!!!");
        }
    }

    @Configuration
    static class ConfigBean3 {
        @Bean
        public InjectedBean injectedBean() {
            System.out.println("1. injectedBean() Called");
            return new InjectedBean();
        }
        @Bean
        public DummyBean3 dummyBean3() {
            DummyBean3 dummyBean3 = new DummyBean3(injectedBean());
            dummyBean3.setInjectedBean(injectedBean());
            return dummyBean3;
        }
    }

    @Test
    @DisplayName(value = "3. Bean LifeCycle Callback [ @PostConstruct, @PreDestroy ]")
    public void lifeCycleTest3() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigBean3.class);
        applicationContext.close();
    }

}
