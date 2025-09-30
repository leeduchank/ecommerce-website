package spring_api.com.hanhle.myspringbootapp.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    @Size(min = 4,message = "USERNAME_INVALID")
    private String username;

    @Size(min = 8 , message = "PASSWORD_INVALID")
    private String password;
    private String email;
}
