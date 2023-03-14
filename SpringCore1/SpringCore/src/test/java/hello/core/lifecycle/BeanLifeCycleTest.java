package hello.core.lifecycle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Configuration
    static class LifeCycleConfig {
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            System.out.println(ANSI_GREEN + "3. ========= Injecting Dependencies =========" + ANSI_RESET);
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }
    }

    @Test
    @DisplayName(value = "Bean Life Cycle Test")
    public void beanLifeCycleTest() {
        System.out.println(ANSI_GREEN + "1. ==================== Creating Spring Container.. ====================" + ANSI_RESET);
        ConfigurableApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = applicationContext.getBean(NetworkClient.class);
        System.out.println(ANSI_GREEN + "5. ==================== Ready to Destroy Spring Container ====================" + ANSI_RESET);
        applicationContext.close();                     // 스프링 컨테이너가 닫히기 직전 Bean 들이 소멸하는 destroy() 호출됨.
        System.out.println(ANSI_GREEN + "7. ==================== Spring Container Closed ====================" + ANSI_RESET);
    }

}
