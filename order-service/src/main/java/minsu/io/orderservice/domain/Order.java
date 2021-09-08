package minsu.io.orderservice.domain;


import lombok.Builder;
import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@Document(collection = "Order")
public class Order {

    private @Id String orderId;

    @NotNull(message = "productId must not be null")
    private String productId;
    @NotNull(message = "qty must not be null")
    private Integer qty;
    @NotNull(message = "unitPrice must not be null")
    private Integer unitPrice;

    @NotNull(message = "totalPrice must not be null")
    private Integer totalPrice;

    @NotNull(message = "userId must not be null")
    private String userId;



    @NotNull(message = "createdAt must not be null")
    private DateTime createdAt;


    @Builder
    public Order(@NotNull(message = "productId must not be null") String productId, @NotNull(message = "qty must not be null") Integer qty, @NotNull(message = "unitPrice must not be null") Integer unitPrice, @NotNull(message = "totalPrice must not be null") Integer totalPrice, @NotNull(message = "userId must not be null") String userId, @NotNull(message = "createdAt must not be null") DateTime createdAt) {

        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
