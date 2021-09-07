package minsu.io.userservice.vo;



import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orderList;

    @Builder
    public ResponseUser(String email, String name, String userId, List<ResponseOrder> orderList) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.orderList = orderList;
    }

    public ResponseUser(String email, String name, String userId) {
        this.email = email;
        this.name = name;
        this.userId = userId;
    }


}
