package spring_api.com.hanhle.myspringbootapp.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_api.com.hanhle.myspringbootapp.dto.request.AuthenticationRequest;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.exception.AppException;
import spring_api.com.hanhle.myspringbootapp.exception.ErrorCode;
import spring_api.com.hanhle.myspringbootapp.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE  ,makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    public boolean login(AuthenticationRequest loginRequest)
    {

        UserEntity userEntity = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(loginRequest.getPassword(),userEntity.getPassword());
    }
}
