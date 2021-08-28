package minsu.io.catalogservice.service;


import lombok.extern.slf4j.Slf4j;
import minsu.io.catalogservice.Dto.ResponseCatalog;
import minsu.io.catalogservice.domain.Product;
import minsu.io.catalogservice.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService{



    private final CatalogRepository repository;

    Environment env;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repository, Environment env) {
        this.repository = repository;
        this.env = env;
    }

    @Autowired

    @Override
    public Flux<Product> getAllCatalogs() {

        return this.repository.findAll()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Catalogs not found")));
    }


}
