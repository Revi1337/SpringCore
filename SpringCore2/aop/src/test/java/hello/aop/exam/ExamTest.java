package hello.aop.exam;

import hello.aop.order.aop.exam.ExamService;
import hello.aop.order.aop.exam.aop.RetryAspect;
import hello.aop.order.aop.exam.aop.TraceAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@SpringBootTest
@Import({TraceAspect.class, RetryAspect.class})
public class ExamTest {

    @Autowired ExamService examService;

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            log.info("client request i = {}", i);
            examService.request("data" + i);
        }
    }

}
