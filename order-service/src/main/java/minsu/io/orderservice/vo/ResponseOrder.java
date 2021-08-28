package minsu.io.orderservice.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private DateTime createdAt;
    private String orderId;

    @Builder
    public ResponseOrder(String productId, Integer qty, Integer unitPrice, Integer totalPrice, DateTime createdAt, String orderId) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderId = orderId;
    }
}
