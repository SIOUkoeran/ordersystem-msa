package minsu.io.orderservice.service;


import lombok.extern.slf4j.Slf4j;
import minsu.io.orderservice.domain.Order;
import minsu.io.orderservice.dto.OrderDto;
import minsu.io.orderservice.repository.OrderRepository;
import minsu.io.orderservice.vo.ResponseOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{


    private final OrderRepository repository;


    @Autowired
    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    public Mono<ResponseOrder> createOrder(Mono<OrderDto> orderDto) {



        return orderDto.map(o-> Order.builder().productId(o.getProductId())
                                                        .qty(o.getQty())
                                                        .totalPrice(o.getTotalPrice())
                                                        .unitPrice(o.getUnitPrice())
                                                        .userId(o.getUserId())
                                                        .createdAt(DateTime.now())
                                                        .build())
                .flatMap(order -> this.repository.save(order))
                .map(order -> ResponseOrder.builder().createdAt(order.getCreatedAt())
                                                        .qty(order.getQty())
                                                        .totalPrice(order.getTotalPrice())
                                                        .unitPrice(order.getUnitPrice())
                                                        .productId(order.getProductId())
                                                        .orderId(order.getOrderId()).build());

    }

    @Override
    public Mono<OrderDto> getOrderByOrderId(String orderId) {

        return this.repository.findByOrderId(orderId)
                .map(order -> OrderDto.builder().orderId(order.getOrderId())
                                                .productId(order.getProductId())
                                                .qty(order.getQty())
                                                .unitPrice(order.getUnitPrice())
                                                .totalPrice(order.getTotalPrice())
                                                .userId(order.getUserId())
                                                .build());
    }

    @Override
    public Flux<Order> getOrdersByUserId(String userId) {

        return this.repository.findByUserId(userId);
    }
}
