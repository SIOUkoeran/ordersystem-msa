package minsu.io.userservice.vo;


import lombok.Data;
import org.joda.time.DateTime;

@Data
public class ResponseOrder {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private DateTime createdAt;

    private String orderId;


}
