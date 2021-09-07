package minsu.io.orderservice.controller;



import lombok.extern.slf4j.Slf4j;

import minsu.io.orderservice.dto.OrderDto;

import minsu.io.orderservice.vo.RequestOrder;
import minsu.io.orderservice.vo.ResponseOrder;
import minsu.io.orderservice.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@Slf4j
@RequestMapping("/order-service/")
public class OrderController {


    private final OrderService orderService;
    private final ReactiveWebServerApplicationContext context;


    @Autowired
    public OrderController(OrderService orderService, ReactiveWebServerApplicationContext context) {
        this.orderService = orderService;
        this.context = context;
    }



    @PostMapping(value = "/{userId}/orders")
    public Mono<ResponseEntity<ResponseOrder>> createOrder(
            @PathVariable("userId") String userId, @RequestBody Mono<RequestOrder> requestOrder) {
        Mono<OrderDto> orderDto = requestOrder.map(r-> OrderDto.builder()
                                                                    .productId(r.getProductId())
                                                                    .unitPrice(r.getUnitPrice())
                                                                    .qty(r.getQty())
                                                                    .userId(userId)
                                                                    .totalPrice(r.getUnitPrice() * r.getQty())
                                                                    .build());


        return this.orderService.createOrder(orderDto)
                .map(responseOrder -> ResponseEntity.status(HttpStatus.CREATED).body(responseOrder));

    }
    @GetMapping("/health_check")
    public Mono<String> status(){
        return Mono.just("It's working in OrderService on Port" + context.getWebServer().getPort());
    }

    @GetMapping(value = "/{userId}/orders")
    public ResponseEntity<Flux<ResponseOrder>> getOrders(@PathVariable("userId") String id){

        Flux<ResponseOrder> orders = this.orderService.getOrdersByUserId(id)
                                                            .map(order->ResponseOrder.builder()
                                                                    .orderId(order.getOrderId())
                                                                    .productId(order.getProductId())
                                                                    .unitPrice(order.getUnitPrice())
                                                                    .totalPrice(order.getTotalPrice())
                                                                    .createdAt(order.getCreatedAt())
                                                                    .qty(order.getQty())
                                                                    .build());
        return ResponseEntity.status(HttpStatus.OK).body(orders);

    }

}
