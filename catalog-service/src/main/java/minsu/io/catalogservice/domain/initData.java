package minsu.io.catalogservice.domain;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class initData {

    @Bean
    CommandLineRunner initializer(MongoOperations  mongo){
        return args ->{
                mongo.save(new Product("CATALOG-0001", "Berlin", 100, 1500));
                mongo.save(new Product("CATALOG-0002", "Tokyo", 100, 900));
                mongo.save(new Product("CATALOG-0003", "Stockholm", 100, 1200));
        };
    }
}
