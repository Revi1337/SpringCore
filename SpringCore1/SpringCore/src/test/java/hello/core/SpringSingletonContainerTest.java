package hello.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringSingletonContainerTest {

    static class DummyClass {
        private int price;
        public void order(String name, int price) {
            System.out.println("name = " + name + " price = " + price);
            this.price = price;
        }
        public int getPrice() {
            return price;
        }
    }

    static class SingletonContainer {
        @Bean
        public DummyClass dummyClass1() {
            return new DummyClass();
        }
        @Bean
        public DummyClass dummyClass2() {
            return new DummyClass();
        }
    }

    @Test
    @DisplayName(value = "기본적으로 ApplicationContext(SpringContainer) 에 애플리케이션 구성정보(SingletonContainer) 넘기면," +
    "SpringContainer 에 구성정보(SingletonContainer) 와 관련된 Bean 들이 등록되게되는데, 이렇게 등록된 Bean 들을 여러번 조회했을 때 Singleton 을 보장해주는 것이다.")
    public void singletonTest() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SingletonContainer.class);
        // TODO 동일한 Bean 을 몇번을 조회해도 Singleton 을 보장해준다.
        DummyClass dummyClass1 = applicationContext.getBean("dummyClass1", DummyClass.class);
        DummyClass dummyClass2 = applicationContext.getBean("dummyClass1", DummyClass.class);
        assertThat(dummyClass1).isSameAs(dummyClass2);
    }

    @Test
    @DisplayName(value = "하지만, Singleton 이기떄문에 값의 공유를 조심해야한다. --> stateless 하게 만들어야 한다.")
    public void singletonContainerShared() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SingletonContainer.class);
        DummyClass dummyClass1 = applicationContext.getBean("dummyClass1", DummyClass.class);
        DummyClass dummyClass2 = applicationContext.getBean("dummyClass1", DummyClass.class);
        System.out.println("dummyClass1 = " + dummyClass1);
        System.out.println("dummyClass2 = " + dummyClass2);

        dummyClass1.order("userA", 10000);
        dummyClass2.order("userB", 20000);

        int price = dummyClass1.getPrice();
        System.out.println("price = " + price);
        assertThat(dummyClass1.getPrice()).isEqualTo(20000);

        DummyClass dummyClass3 = applicationContext.getBean("dummyClass2", DummyClass.class);
        DummyClass dummyClass4 = applicationContext.getBean("dummyClass2", DummyClass.class);
        System.out.println("dummyClass3 = " + dummyClass3);
        System.out.println("dummyClass4 = " + dummyClass4);
    }

}
