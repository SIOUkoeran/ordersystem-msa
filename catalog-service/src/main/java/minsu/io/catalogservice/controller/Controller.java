package minsu.io.catalogservice.controller;


import lombok.extern.slf4j.Slf4j;
import minsu.io.catalogservice.Dto.ResponseCatalog;
import minsu.io.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping(value = "/catalog-service/")
public class Controller {

    private final CatalogService catalogService;

    @Autowired
    public Controller(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(value = "catalogs")
    public ResponseEntity<Flux<ResponseCatalog>> getAllCatalog(){
        Flux<ResponseCatalog> catalogs =  this.catalogService.getAllCatalogs()
                                            .map(catalog-> ResponseCatalog.builder()
                                                            .productId(catalog.getProductId())
                                                            .productName(catalog.getProductName())
                                                            .stock(catalog.getStock())
                                                            .unitPrice(catalog.getUnitPrice())
                                                            .createAt(catalog.getCreatedAt()).build()
                                            );
        

        return ResponseEntity.ok(catalogs);
    }
}
