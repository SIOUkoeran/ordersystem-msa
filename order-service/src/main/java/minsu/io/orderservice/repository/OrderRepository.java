package minsu.io.orderservice.repository;


import minsu.io.orderservice.domain.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, String> {


    Mono<Order> findByOrderId(String orderId);

    Flux<Order> findByUserId(String UserID);

}
