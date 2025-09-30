package spring_api.com.hanhle.myspringbootapp.dto;


import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
