package hello.proxy.trace.threadlocal;

import hello.proxy.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

// TODO 해당 스레드가 쓰레드 로컬을 모두 사용하고 나면, ThreadLocal.remove() 를 호출해서 쓰레드 로컬에 저장된 값을 제거해야 한다.

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService service = new ThreadLocalService();

    @Test
    public void field() {
        log.info("main start");
        Runnable userA = () -> service.logic("userA");
        Runnable userB = () -> service.logic("userB");

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


