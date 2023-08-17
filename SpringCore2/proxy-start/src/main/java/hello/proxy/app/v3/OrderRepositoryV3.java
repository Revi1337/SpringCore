package hello.proxy.app.v3;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV3 {

    @PostConstruct
    public void init() {
        System.out.println("OrderRepositoryV3.init");
    }

    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
