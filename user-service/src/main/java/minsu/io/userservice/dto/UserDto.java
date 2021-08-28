package minsu.io.userservice.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import minsu.io.userservice.vo.ResponseOrder;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Builder
@ToString
@AllArgsConstructor
public class UserDto {


    private String email;
    private String userId;
    private String name;
    private String pwd;


    private DateTime createdAt;

    private String encryptedPwd;

    private List<ResponseOrder> orderList;



}
