package hello.core.beanfind;

import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ApplicationContextSameBeanFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    static class MemoryMemberRepository2 implements MemberRepository{
        @Override
        public void save(Member member) {}
        @Override
        public Member findById(Long memberId) {return null;}
    }


    @Configuration
    static class SameBeanConfig {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemberRepository memberRepository3() {
            return new MemoryMemberRepository();
        }
        @Bean
        public MemoryMemberRepository2 memberRepository4() {
            return new MemoryMemberRepository2();
        }
    }

    @Test
    public void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + " object = " + bean);
            }
        }
    }

    @Test
    @DisplayName(value = "타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생")
    public void findBeanByTypeDuplicate() {
//         MemberRepository bean = ac.getBean(MemberRepository.class);
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName(value = "타입으로 조회 시 같은 타입이 둘 이상 있어 중복 오류가 발생하면 빈 이름을 지정하면 된다.")
    public void findBeanByTypeDuplicateSolving() {
        MemberRepository bean = ac.getBean("memberRepository1", MemberRepository.class);
        assertThat(bean).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName(value = "특정 타입을 모두 조회하기")
    public void findAllBeanByType() {
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for (String key : beansOfType.keySet())
            System.out.println("key = " + key + ", value = " + beansOfType.get(key));
        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(4);

        assertThat(ac.getBeansOfType(MemberRepository.class).size()).isEqualTo(4);
        assertThat(ac.getBeansOfType(MemoryMemberRepository.class).size()).isEqualTo(3);
        assertThat(ac.getBeansOfType(MemoryMemberRepository2.class).size()).isEqualTo(1);
    }

    @Test
    @DisplayName(value = "이름으로 조회시 같은 타입이 둘 이상 있어도, 중복 오류가 발생하지 않음.")
    public void findBeanByNameDuplicate() {
        Object memberRepository1 = ac.getBean("memberRepository1");
        System.out.println("memberRepository1 = " + memberRepository1.getClass());
    }

}
