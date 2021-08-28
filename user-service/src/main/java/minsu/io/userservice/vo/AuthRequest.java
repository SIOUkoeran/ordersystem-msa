package minsu.io.userservice.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotNull(message = "Email cannot be null")
    @Size(min =2, message = "Email not be less that")
    private String email;


    @NotNull(message = "password cannot be null")
    @Size(min = 5, message = "password not be less that")
    private String password;



}
