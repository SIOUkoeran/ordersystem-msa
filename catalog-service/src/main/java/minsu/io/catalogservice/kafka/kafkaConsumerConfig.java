package minsu.io.catalogservice.kafka;



import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class kafkaConsumerConfig {

    @Bean
    public ReceiverOptions<String, Object> kafkaReceiverOptions(@Value(value = "${FAKE_CONSUMER_DTO_TOPIC}")String topics, KafkaProperties kafkaProperties){
        ReceiverOptions<String , Object> basicReceiverOptions = ReceiverOptions.create(getProducerProps());
        return basicReceiverOptions.subscription(Collections.singletonList(topics));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Object> reactiveKafkaConsumerTemplate(ReceiverOptions<String, Object> kafkaReceiverOptions){
        return new ReactiveKafkaConsumerTemplate<String, Object>(kafkaReceiverOptions);
    }

    private Map<String, Object> getProducerProps(){
        return new HashMap<>(){{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
            put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        }};
    }


}
