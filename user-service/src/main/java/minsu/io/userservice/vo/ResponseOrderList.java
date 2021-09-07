package minsu.io.userservice.vo;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import minsu.io.userservice.domain.User;
import minsu.io.userservice.dto.UserDto;



@Getter @Setter
@Builder
public class ResponseOrderList {

    private User user;
    private ResponseOrder responseOrder;

}
