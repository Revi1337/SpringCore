package hello.proxy.app.v3;


import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV3 {

    private final OrderRepositoryV3 repository;

    @PostConstruct
    public void init() {
        System.out.println("OrderServiceV3.init");
    }

    public OrderServiceV3(OrderRepositoryV3 repository) {
        this.repository = repository;
    }

    public void orderItem(String itemId) {
        repository.save(itemId);
    }
}
