package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac =
            new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName(value = "스프링컨테이너의 모든 빈 출력")
    public void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName(value = "애플리케이션 빈 출력")
    public void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);         // Bean 에 대한 정보들 (메타데이타 정보)
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {                // 만약 Bean 의 대한 정보에서 Role 이 ROLE_APPLICATION 이면 (스프링 내부에서 등록한 빈이 아니라, 내가 애플리케이션을 개발하기 위해서 등록한 빈, 혹은 외부 라이브러리)
                Object bean = ac.getBean(beanDefinitionName);                                 // ROLE_APPLICATION : 일반적으로 사용자가 정의한 빈
                System.out.println("name = " + beanDefinitionName + " object = " + bean);     // ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈
            }
        }
    }
}
