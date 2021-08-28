package minsu.io.catalogservice.service;


import minsu.io.catalogservice.domain.Product;
import reactor.core.publisher.Flux;

public interface CatalogService {


    Flux<Product> getAllCatalogs();


}
