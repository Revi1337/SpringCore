package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoAppConfigTest {

    @Test
    @DisplayName(value = "@ComponentScan 을 통해 Bean 을 정상적을 등록하고 스캔하는지 테스트")
    public void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        MemberService bean = ac.getBean(MemberService.class);
        assertThat(bean).isInstanceOf(MemberService.class);
        assertThat(bean).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName(value = "@ComponentScan 을 통해 등록한 Bean 중에 내가 직접 등록만 Bean 들만 조회")
    public void showBeanByComponentScanWhereIRegister() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("BeanName = " + beanDefinitionName + ", Object = " + beanDefinition);
            }
        }
    }

}
