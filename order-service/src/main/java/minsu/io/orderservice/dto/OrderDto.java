package minsu.io.orderservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class OrderDto {

    private String productId;

    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private String userId;

    @Builder
    public OrderDto(String productId, Integer qty, Integer unitPrice, Integer totalPrice, String orderId, String userId) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.orderId = orderId;
        this.userId = userId;
    }
}
