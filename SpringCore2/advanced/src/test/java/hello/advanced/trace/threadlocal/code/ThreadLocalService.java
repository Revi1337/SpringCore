package hello.advanced.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    // TODO 해당 스레드가 쓰레드 로컬을 모두 사용하고 나면, ThreadLocal.remove() 를 호출해서 쓰레드 로컬에 저장된 값을 제거해야 한다.

    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("저장 name = {} --> nameStore = {}", name, nameStore.get());     // TODO 둘다 null. 이 이유는 별도의 저장소에 따로 저장했기 때문임.
        nameStore.set(name);
        sleep(1000);
        log.info("조회 nameStore = {}", nameStore.get());
        return nameStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
