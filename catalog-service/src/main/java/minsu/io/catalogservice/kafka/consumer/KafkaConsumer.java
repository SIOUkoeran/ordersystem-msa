package minsu.io.catalogservice.kafka.consumer;


import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import minsu.io.catalogservice.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class KafkaConsumer {

    private final ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate;

    public KafkaConsumer(ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate) {
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
    }


    @KafkaListener(topics = "example-order-topic")
    public void processMessage(String kafkaMessage){

    }



}
