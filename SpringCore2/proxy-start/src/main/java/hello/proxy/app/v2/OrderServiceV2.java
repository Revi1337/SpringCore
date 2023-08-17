package hello.proxy.app.v2;


import hello.proxy.app.v1.OrderServiceV1;

public class OrderServiceV2 implements OrderServiceV1 {

    private final OrderRepositoryV2 repository;

    public OrderServiceV2(OrderRepositoryV2 repository) {
        this.repository = repository;
    }

    @Override
    public void orderItem(String itemId) {
        repository.save(itemId);
    }
}
