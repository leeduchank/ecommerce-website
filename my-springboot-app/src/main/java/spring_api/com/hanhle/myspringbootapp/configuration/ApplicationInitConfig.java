package spring_api.com.hanhle.myspringbootapp.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring_api.com.hanhle.myspringbootapp.dto.request.UserRequest;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.enums.Roles;
import spring_api.com.hanhle.myspringbootapp.repositories.UserRepository;

import java.util.HashSet;



@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationInitConfig {


    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
            return args -> {
                if (userRepository.findByUsername("admin").isEmpty()){
                    HashSet<String> roles = new HashSet<>();
                    roles.add(Roles.ADMIN.name());
                    UserEntity userEntity = UserEntity.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(roles)
                            .build();

                    userRepository.save(userEntity);
                    log.warn("Admin has created with username and password = Admin");
                }
            };
        }
}
