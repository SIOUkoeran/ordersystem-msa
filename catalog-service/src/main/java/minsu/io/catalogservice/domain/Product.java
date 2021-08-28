package minsu.io.catalogservice.domain;


import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Data
@Document(collection = "Product")
public class Product {


    private @Id String id;

    @NotNull(message = "productId must not be null")
    private String productId;

    @NotNull(message = "productName must not be null")
    private String productName;

    @NotNull(message = "stock must not be null")
    private Integer stock;

    @NotNull(message = "unitPrice must not be null")
    private Integer unitPrice;

    @NotNull(message = "createdAt must not be null")
    private DateTime createdAt;

    public Product(String productId, String productName, Integer stock, Integer unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
    }
}
