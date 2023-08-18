package hello.advanced;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;

@Slf4j
public class SelfDesignPatternTest {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static class Controller1 {

        private final Service1 service1;

        public Controller1(Service1 service1) {
            this.service1 = service1;
        }

        public void task() {
            System.out.println("Controller1.task() - start");
            service1.task();
            System.out.println("Controller1.task() - end");
        }
    }

    static class Service1 {

        private final Repository1 repository1;

        public Service1(Repository1 repository1) {
            this.repository1 = repository1;
        }

        public void task() {
            System.out.println("Service1.task() - start");
            repository1.task();
            System.out.println("Service1.task() - end");
        }
    }

    static class Repository1 {
        public void task() {
            System.out.println("Repository1.task() - start");
            System.out.println("Repository1 Working....");
            System.out.println("Repository1.task() - end");
        }
    }

    @Configuration
    static class Config{
        @Bean
        public Controller1 controller1() {
            return new Controller1(service1());
        }
        @Bean
        public Service1 service1() {
            return new Service1(repository1());
        }
        @Bean
        public Repository1 repository1() {
            return new Repository1();
        }
    }

    @Test
    public void beforeTemplateMethodPattern() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(Config.class);
        Controller1 controller = applicationContext.getBean(Controller1.class);

        controller.task();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    abstract static class Wrapper{

        public void executor() {
            String currentMethodName = null;
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            if (stackTraceElements.length >= 2) {
                StackTraceElement caller = stackTraceElements[1];
                currentMethodName = caller.getMethodName();
            }

            System.out.println(currentMethodName + "()" + " - start");
            task();
            System.out.println(currentMethodName + "()" + " - end");
        }

        public abstract void task();
    }


    static class Controller2 extends Wrapper{
        private final Service2 service;
        public Controller2(Service2 service) { this.service = service; }

        @Override
        public void task() {
            service.task();
        }
    }

    static class Service2 extends Wrapper{
        private final Repository2 repository;
        public Service2(Repository2 repository) { this.repository = repository; }

        @Override
        public void task() {
            repository.task();
        }
    }

    static class Repository2 extends Wrapper{
        @Override
        public void task() {
            System.out.println("Repository1 Working....");
        }
    }

    @Configuration
    static class Config2{
        @Bean
        public Controller2 controller2() {
            return new Controller2(service2());
        }
        @Bean
        public Service2 service2() {
            return new Service2(repository2());
        }
        @Bean
        public Repository2 repository2() {
            return new Repository2();
        }
    }

    @Test
    public void afterTemplateMethodPattern() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(Config2.class);

        Controller2 controller2 = applicationContext.getBean(Controller2.class);
        controller2.executor();

        Repository2 repository2 = applicationContext.getBean(Repository2.class);
        repository2.executor();

        Service2 service2 = applicationContext.getBean(Service2.class);
        service2.executor();
    }

    ////////////////////////////////////////////////////////////////////////////////////

    static class Context {
        private Strategy strategy;
        public Context(Strategy strategy) { this.strategy = strategy; }
        public void executor() {
            String currentMethodName = null;
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            if (stackTraceElements.length >= 2) {
                StackTraceElement caller = stackTraceElements[1];
                currentMethodName = caller.getMethodName();
            }

            System.out.println(getClass() + "." + currentMethodName + "()" + " - start");
            strategy.task();
            System.out.println(getClass() + "." + currentMethodName + "()" + " - end");
        }
    }

    interface Strategy {
        void task();
    }

    static class Service3 implements Strategy{
        @Override
        public void task() {
            System.out.println("Service3 Working...");
        }
    }

    @Test
    @DisplayName(value = "Strategy Pattern 1 [생성자]")
    public void afterStrategyPatternByConstructorTest() {
        Service3 service3 = new Service3();
        Context context = new Context(service3);

        context.executor();
        context.executor();
        context.executor();
    }

    ////////////////////////////////////////////////////////////////////////////////////

    static class Context2 {
        public void executor(Strategy2 strategy) {
            String currentClassName = strategy.getClass().getName();
            System.out.println(currentClassName + "()" + " - start");
            strategy.task();
            System.out.println(currentClassName + "()" + " - end");
        }
    }

    interface Strategy2 {
        void task();
    }

    @Test
    @DisplayName(value = "Strategy Pattern 2 [매개변수]")
    public void afterStrategyPatternBySetterTest() {
        Context2 context2 = new Context2();

        context2.executor(() -> System.out.println("SelfTemplateMethodPatternTest.task"));
    }

    ////////////////////////////////////////////////////////////////////////////////////

}

