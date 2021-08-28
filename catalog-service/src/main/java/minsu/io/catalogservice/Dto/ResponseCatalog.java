package minsu.io.catalogservice.Dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCatalog {


    private String productId;
    private String productName;

    private Integer stock;
    private Integer unitPrice;
    private DateTime createAt;


    @Builder
    public ResponseCatalog(String productId, String productName, Integer stock, Integer unitPrice, DateTime createAt) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.unitPrice = unitPrice;
        this.createAt = createAt;
    }
}
