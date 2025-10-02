package spring_api.com.hanhle.myspringbootapp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

public class AuthenticationResponse {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
        @FieldDefaults(level = AccessLevel.PRIVATE)
    public class AuthenticationRequest {
        boolean authenicated;
    }
}
