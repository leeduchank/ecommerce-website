package spring_api.com.hanhle.myspringbootapp.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {


        Long id;
        @Size(min = 4, message = "USERNAME_INVALID")
        String username;

        @Size(min = 8, message = "PASSWORD_INVALID")
        String password;
        String email;
        Set<String> roles;
    }
