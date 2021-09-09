package minsu.io.catalogservice.kafka.consumer;


import lombok.extern.slf4j.Slf4j;
import minsu.io.catalogservice.Dto.OrderDto;
import minsu.io.catalogservice.domain.Product;
import minsu.io.catalogservice.repository.CatalogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate;
    private final CatalogRepository repository;
    public KafkaConsumer(ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate, CatalogRepository repository) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
        this.repository = repository;
    }


    @KafkaListener(topics = "example-order-topic")
    public Mono<Void> updateStock(Mono<OrderDto> orderDto){

        // orderDto -> receiverRecord 변경하기

        return orderDto.map(order ->
            this.repository.findByProductId(order.getOrderId())
                    .flatMap( product -> {
                        product.setStock(product.getStock()-order.getQty());
                        this.repository.save(product);
                        return null;
                    })).then();

    }



}
