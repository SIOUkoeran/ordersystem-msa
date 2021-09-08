package minsu.io.orderservice.kafka;



import lombok.extern.slf4j.Slf4j;

import minsu.io.orderservice.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class KafkaProducer {

    private final ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate;

    @Autowired
    public KafkaProducer(ReactiveKafkaProducerTemplate<String, Object> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    public Mono<Boolean> send(String topic, Mono<OrderDto> orderDto){
        log.info("message =====> {}", topic);


        return reactiveKafkaProducerTemplate.send(topic, orderDto)
                .doOnSuccess(voidSenderResult -> log.info("send {} offset{}", orderDto, voidSenderResult.recordMetadata().offset()))
                .then()
                .map(result->true)
                .onErrorResume(e ->{
                    log.info("error {}", e);
                    return Mono.just(false);
                });




    }
}
