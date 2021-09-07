package minsu.io.userservice.dto;



import lombok.*;
import minsu.io.userservice.domain.User;
import minsu.io.userservice.vo.ResponseOrder;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter @Getter
@ToString
@Builder
public class UserDto {


    private String email;
    private String userId;
    private String name;
    private String pwd;


    private DateTime createdAt;

    private String encryptedPwd;

    private List<ResponseOrder> orderList;

//    public static UserDto from(User userDto, List<ResponseOrder> orderList){
//
//        return new UserDto(userDto.getEmail(), userDto.getId(), userDto.getName(), userDto.getPassword(), userDto.getCreatedAt()
//        , orderList);
//
//    }
//
//    public UserDto(String email, String userId, String name, String pwd, DateTime createdAt,List<ResponseOrder> orderList) {
//        this.email = email;
//        this.userId = userId;
//        this.name = name;
//        this.pwd = pwd;
//        this.createdAt = createdAt;
//        this.encryptedPwd = encryptedPwd;
//        this.orderList = orderList;
//    }
}
