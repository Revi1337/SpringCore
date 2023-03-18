package hello.core;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Scope(value = "prototype")
    static class PrototypeBean {
        @PostConstruct
        public void postConstruct() {
            System.out.println("Prototype @PostConstruct");
        }
        @PreDestroy
        public void preDestroy() {
            System.out.println("Prototype @PreDestroy didn't Called");
        }
    }

    @Scope(value = "singleton")
    static class SingletonBean {
        @PostConstruct
        public void postConstruct() {
            System.out.println("Singleton @PostConstruct");
        }
        @PreDestroy
        public void preDestroy() {
            System.out.println("Singleton @PreDestroy Called");
        }
    }

    @Scope(value = "singleton")
    static class SingletonComposePrototypeBean {

        private PrototypeBean prototypeBean;

        public SingletonComposePrototypeBean() {
            System.out.println(this.getClass().getSimpleName() + " No Args Called");
        }

        @Autowired
        public SingletonComposePrototypeBean(PrototypeBean prototypeBean) {
            System.out.println(this.getClass().getSimpleName() + " Req Args Called");
            this.prototypeBean = prototypeBean;
        }

        @PostConstruct
        public void postConstruct() {
            System.out.println("SingletonComposePrototypeBean @PostConstruct Called");
        }

        @PreDestroy
        public void preDestroy() {
            System.out.println("SingletonComposePrototypeBean @PreDestroy Called");
        }

        public PrototypeBean getPrototypeBean() {
            return prototypeBean;
        }
    }

    @Test
    @DisplayName(value =
            "Prototype Scope Bean 은 Spring Container 가 생성될때 생성되는것이 아닌, Bean 을 조회하는 시점에 프로토타입 빈을 생성하여. 필요한 의존관계를 주입하고 초기화(@PostConstruct) 까지 호출하여 Client 에게 반환해준다. " +
            "Bean 조회시 매번 새로운 인스턴스를 생성하므로 조회할때마다 @PostConstruct 가 호출된다.")
    public void beanScopeTest() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrototypeBean.class, SingletonBean.class);
        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);

        applicationContext.close();
    }

    @Test
    @DisplayName(value =
            "Prototype Scope Bean 을 Spring Container 에 조회하면 스프링컨테이너는 매번 새로운 인스턴스를 생성해서 Client 에게 반환해준다." +
            "Client 에게 Bean 을 반환한 이후, 스프링 컨테이너는 해당 Bean 을 관리하지 않기 때문에 Spring Container 가 닫히는 시점에 @PreDestroy 가 호출되지 않는다")
    public void beanScopeTest2() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrototypeBean.class, SingletonBean.class);

        SingletonBean singletonBean1 = applicationContext.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = applicationContext.getBean(SingletonBean.class);
        System.out.println("singletonBean1 = " + singletonBean1);
        System.out.println("singletonBean2 = " + singletonBean2);
        assertThat(singletonBean1).isSameAs(singletonBean2);

        PrototypeBean prototypeBean1 = applicationContext.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = applicationContext.getBean(PrototypeBean.class);
        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        // Singleton Scope 인 Bean 은 Bean 생성과 의존성 주입 후, SpringContainer 가 관리하기때문에 SpringContainer 가 닫힐때 @PreDestroy 가 호출됨.
        // Prototype Scope 인 Bean 은 Bean 생성과 의존성 주입 후, SpringContainer 가 버리기떄문에(관리 X) SpringContainer 가 닫힐때 @PreDestroy 가 호출되지 않음.
        applicationContext.close();
    }

    @Test
    @DisplayName(value =
                    "Singleton Scope 인 Bean 이 Prototype Scope 인 Bean 의 의존성을 갖고있을때의 주의점" +
                    "일반적인 Prototype Scope Bean 를 조회할때 항상 새로운 인스턴스를 생성함. 하지만, Singleton Scope 의 Bean 이 Prototype Scope Bean 에 대한" +
                    "의존성을 갖고있으면 Singleton Bean 가 의존하고있는 Prototype Bean 조회 시, Prototype 패러다임에 맞지않게 항상 같은 인스턴스가 출력되는 문제가 발생함.")
    public void issueWhenSingletonComposePrototype() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrototypeBean.class, SingletonBean.class, SingletonComposePrototypeBean.class);

        SingletonComposePrototypeBean singletonComposePrototypeBean1 = applicationContext.getBean(SingletonComposePrototypeBean.class);
        SingletonComposePrototypeBean singletonComposePrototypeBean2 = applicationContext.getBean(SingletonComposePrototypeBean.class);
        SingletonComposePrototypeBean singletonComposePrototypeBean3 = applicationContext.getBean(SingletonComposePrototypeBean.class);

        System.out.println("singletonComposePrototypeBean1 = " + singletonComposePrototypeBean1);
        System.out.println("singletonComposePrototypeBean2 = " + singletonComposePrototypeBean2);
        System.out.println("singletonComposePrototypeBean3 = " + singletonComposePrototypeBean3);

        System.out.println("singletonComposePrototypeBean1 = " + singletonComposePrototypeBean1.getPrototypeBean());
        System.out.println("singletonComposePrototypeBean2 = " + singletonComposePrototypeBean2.getPrototypeBean());
        System.out.println("singletonComposePrototypeBean3 = " + singletonComposePrototypeBean3.getPrototypeBean());
    }

    // #############################################################################################################################
    @Scope(value = "singleton")
    static class SolvingSingletonComposePrototypeBean {
        private final ApplicationContext applicationContext;

        @Autowired
        public SolvingSingletonComposePrototypeBean(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public PrototypeBean getPrototypeBean() {
            return applicationContext.getBean(PrototypeBean.class);
        }
    }

    @Test
    @DisplayName(value = "Singleton Scope 인 Bean 이 Prototype Scope 인 Bean 의 의존성을 갖고있을때의 주의점 해결 1 [ ApplicationContext 주입 ]")
    public void solvingSingletonComposePrototype1() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrototypeBean.class, SingletonBean.class, SolvingSingletonComposePrototypeBean.class);
        SolvingSingletonComposePrototypeBean solvingSingletonComposePrototypeBean1 =
                applicationContext.getBean(SolvingSingletonComposePrototypeBean.class);
        SolvingSingletonComposePrototypeBean solvingSingletonComposePrototypeBean2 =
                applicationContext.getBean(SolvingSingletonComposePrototypeBean.class);
        SolvingSingletonComposePrototypeBean solvingSingletonComposePrototypeBean3 =
                applicationContext.getBean(SolvingSingletonComposePrototypeBean.class);

        System.out.println("solvingSingletonComposePrototypeBean1 = " + solvingSingletonComposePrototypeBean1.getPrototypeBean());
        System.out.println("solvingSingletonComposePrototypeBean2 = " + solvingSingletonComposePrototypeBean2.getPrototypeBean());
        System.out.println("solvingSingletonComposePrototypeBean3 = " + solvingSingletonComposePrototypeBean3.getPrototypeBean());
    }

    // #############################################################################################################################

    static class SolvingSingletonComposePrototypeBean2 {
        private final ObjectProvider<PrototypeBean> objectProvider;

        public SolvingSingletonComposePrototypeBean2(ObjectProvider<PrototypeBean> objectProvider) {
            this.objectProvider = objectProvider;
        }

        public PrototypeBean getPrototypeBean() {
            return objectProvider.getObject();
        }
    }

    @Test
    @DisplayName(value = "Singleton Scope 인 Bean 이 Prototype Scope 인 Bean 의 의존성을 갖고있을때의 주의점 해결 2 [ ObjectProvider<> 주입 ]")
    public void solvingSingletonComposePrototype2() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(PrototypeBean.class, SingletonBean.class, SolvingSingletonComposePrototypeBean2.class);
        SolvingSingletonComposePrototypeBean2 solvingSingletonComposePrototypeBean1 =
                applicationContext.getBean(SolvingSingletonComposePrototypeBean2.class);
        SolvingSingletonComposePrototypeBean2 solvingSingletonComposePrototypeBean2 =
                applicationContext.getBean(SolvingSingletonComposePrototypeBean2.class);
        SolvingSingletonComposePrototypeBean2 solvingSingletonComposePrototypeBean3 =
                applicationContext.getBean(SolvingSingletonComposePrototypeBean2.class);

        PrototypeBean prototypeBean1 = solvingSingletonComposePrototypeBean1.getPrototypeBean();
        PrototypeBean prototypeBean2 = solvingSingletonComposePrototypeBean2.getPrototypeBean();
        PrototypeBean prototypeBean3 = solvingSingletonComposePrototypeBean3.getPrototypeBean();

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        System.out.println("prototypeBean3 = " + prototypeBean3);

        System.out.println("prototypeBean1 = " + prototypeBean1.getClass().getSimpleName());
        System.out.println("prototypeBean2 = " + prototypeBean2.getClass().getSimpleName());
        System.out.println("prototypeBean3 = " + prototypeBean3.getClass().getSimpleName());
    }
}
