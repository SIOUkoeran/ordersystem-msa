package minsu.io.orderservice.service;

import minsu.io.orderservice.domain.Order;
import minsu.io.orderservice.dto.OrderDto;

import minsu.io.orderservice.vo.ResponseOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<ResponseOrder> createOrder(Mono<OrderDto> orderDto);
    Mono<OrderDto> getOrderByOrderId(String orderId);
    Flux<Order> getOrdersByUserId(String userId);
}
