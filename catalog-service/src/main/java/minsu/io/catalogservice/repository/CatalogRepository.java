package minsu.io.catalogservice.repository;

import minsu.io.catalogservice.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface CatalogRepository extends ReactiveCrudRepository<Product, String> {

    Mono<Product> findByProductId(String productId);
}
