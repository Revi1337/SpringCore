package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

// TODO 동시성 문제는 지역변수에서는 발생하지 않음 --> 지연변수는 스레드마다 각각 다른 메모리 영역이 할당되기 때문임.
//  동시성 문제가 발생하는 곳은 같은 인스턴스의 필드 (주로 싱글톤에서 발생), 또는 Static 같은 공용 필드에 접근할 떄 발생한다.
//  동시성 문제는 "값을 읽기만하면 발생하지않음". 하지만 어디선가 값을 변경하면 발생함.
//  이렇게 싱글톤 객체의 필드를 사용하면서 동시성 문제를 해결하려면 ThreadLocal 을 사용해야 한다.

@Slf4j
public class FieldServiceTest {

    FieldService fieldService = new FieldService();

    @Test
    public void field() {
        log.info("main start");
        Runnable userA = () -> fieldService.logic("userA");
        Runnable userB = () -> fieldService.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread - A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread - B");

        threadA.start();
//        sleep(2000);            // TODO 동시성이 발생하지 않는 코드 (스레드가 끝나고 다른 스레드가 생성됨.)
        sleep(100);            // TODO 동시성이 발생하는 코드 (다른 child Thread 가 끝나기전에 또다른 child Thread 가 실행) --> 여러 스레드가 동시에 같은 인스턴스의 필드 값을 변경하기 때문에 발생. --> 특히 스프링 Bean 처럼 싱글톤 객체의 필드를 변경하며 사용할때 동시성 문제를 조심해야 한다.
        threadB.start();

        sleep(3000);
        log.info("main exit");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


