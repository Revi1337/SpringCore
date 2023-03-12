package hello.core.scan;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;


public class BeanConflictTest {

    @Component(value = "duplicateBean2") static class DuplicateBean1 {} // 이름을 duplicateBean1, duplicateBean2 로 변경하며 테스트해볼 것.
    @Component(value = "duplicateBean1") static class DuplicateBean2 {}
    @Configuration @ComponentScan
    static class DuplicateAutoConfig {}

    @Test
    @DisplayName(value = "자동 빈 등록 vs 자동 빈 등록 - Bean 이름이 충돌하여 Exception 이 터짐.")
    public void autoVSAuto() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DuplicateAutoConfig.class);
    }

// =====================================================================================================

    @Component(value = "duplicateBean3") static class DuplicateBean3 {}
    @Component(value = "duplicateBean4") static class DuplicateBean4 {}
    @Configuration @ComponentScan
    static class DuplicateAutoConfig2 {
        @Bean
        public DuplicateBean3 DuplicateBean3() {
            return new DuplicateBean3();
        }
    }

    @Test
    @DisplayName(value = "수동 빈 등록 vs 자동 빈 등록 - 수동으로 등록한 Bean 이름으로 Overriding 됨. ")
    public void handmadeVSAuto() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DuplicateAutoConfig2.class);
        // 스프링 컨테이너 초기화시 로그에 Overriding bean definition for bean 'duplicateBean1' with a different definition: replacing... 등이 발생함.
    }

}
