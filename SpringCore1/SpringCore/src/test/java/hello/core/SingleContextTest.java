package hello.core;

import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleContextTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingleConfiguration.class);

    static class MemoryMemberRepository2 implements MemberRepository{
        @Override
        public void save(Member member) {}
        @Override
        public Member findById(Long memberId) {return null;}
    }

    @Configuration
    static class SingleConfiguration {
        @Bean
        public MemberRepository memoryMemberRepository() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memoryMemberRepository2() { return new MemoryMemberRepository(); }
        @Bean
        public MemberRepository memoryMemberRepository3() { return new MemoryMemberRepository(); }
        @Bean
        public MemoryMemberRepository2 memoryMemberRepository4() {
            return new MemoryMemberRepository2();
        }
    }

    @Test
    @DisplayName(value = "모든 Bean 들을 출력")
    public void showAllBeans() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("Bean Name = " + beanDefinitionName + ", Bean Type = " + bean);
        }
    }

    @Test
    @DisplayName(value = "직접 정의한 Bean 들만 출력")
    public void showCustomBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinition = " + beanDefinition);
            }
        }
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 1 - Bean 이름과 타입을 모두 명시하여 조회")
    public void selectBeanByNameAndType() {
        MemberRepository memoryMemberRepository2 = ac.getBean("memoryMemberRepository2", MemberRepository.class);
        assertThat(memoryMemberRepository2).isInstanceOf(MemberRepository.class);
        assertThat(memoryMemberRepository2).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 1-1 - Bean 이름과 타입을 모두 명시하여 조회했는데 Bean 이 없으면 Exception 발생")
    public void selectBeanByNameAndTypeWhenExistsNoBean() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("DUMMYYYYYYYYYYYY", MemberRepository.class));
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 2 - Bean 이름으로만 조회")
    public void selectBeanByName() {
        Object memoryMemberRepository = ac.getBean("memoryMemberRepository");
        assertThat(memoryMemberRepository).isInstanceOf(MemberRepository.class);
        assertThat(memoryMemberRepository).isInstanceOf(MemoryMemberRepository.class);
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 2-1 - Bean 이름으로만 조회했는데 Bean 이 없으면 Exception 발생")
    public void selectBeanByNameWhenExistsNoBean() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("xxxxx"));
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 3 - Bean 타입으로만 조회")
    public void selectBeanByType() {
        MemoryMemberRepository2 bean = ac.getBean(MemoryMemberRepository2.class);
        assertThat(bean).isInstanceOf(MemberRepository.class);
        assertThat(bean).isInstanceOf(MemoryMemberRepository2.class);
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 3-1 - Bean 타임으로만 조회했는데 Bean 이 없으면 Exception 발생")
    public void selectBeanByTypeWhenExistsNoBean() {
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean(List.class));
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 3-2 - Bean 을 타입으로만 조회할때 다른 동일한 타입의 빈이 존재하면 Exception 발생한다")
    public void selectBeanByTypeWhenExistSameType() {
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName(value = "Bean 을 조회하는 방법 4 - 특정 타입의 Bean 들만 조회")
    public void selectSameTypeOfBean() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + ", value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName(value = "[IMPORTANT] 부모타입의 Bean 을 조회하면 자식타입의 Bean 모두 조회된다.")
    public void listBeansWhenSelectParentTypeBean() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet())
            System.out.println("key = " + key + ", value = " + beansOfType.get(key));
        assertThat(ac.getBeansOfType(MemberRepository.class).size())
                .as("부모인 MemberRepository 를 조회하면 구현체 타입의 Bean 까지 모두 출력").isEqualTo(4);
        assertThat(ac.getBeansOfType(MemoryMemberRepository.class).size())
                .as("구현체인 MemberRepository 를 조회하면 구현체 타입의 Bean 만 조회").isEqualTo(3);
        assertThat(ac.getBeansOfType(MemoryMemberRepository2.class).size())
                .as("구현체인 MemberRepository2 를 조회하면 구현체 타입의 Bean 만 조회").isEqualTo(1);
    }

}
