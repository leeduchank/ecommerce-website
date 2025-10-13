package spring_api.com.hanhle.myspringbootapp.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_api.com.hanhle.myspringbootapp.dto.request.AuthenticationRequest;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
import spring_api.com.hanhle.myspringbootapp.dto.response.UserResponse;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.enums.Roles;
import spring_api.com.hanhle.myspringbootapp.exception.AppException;
import spring_api.com.hanhle.myspringbootapp.exception.ErrorCode;
import spring_api.com.hanhle.myspringbootapp.mapper.UserMapper;
import spring_api.com.hanhle.myspringbootapp.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE  ,makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    public UserResponse register (UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity user = userMapper.toUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
        user.setRoles(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    };

    public UserResponse update(Long id,UserDto userDto){
        UserEntity userEntity = userMapper.toUserEntity(getUserById(id));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userMapper.updateUser(userEntity,userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return (userMapper.toUserResponse(userRepository.save(userEntity)));
    }


    public UserResponse userGetInfo(){
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UserEntity userEntity = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
    return userMapper.toUserResponse(userEntity);
    }


    public void delete(Long id){
        UserResponse userResponse = getUserById(id);
        UserEntity userEntity = userMapper.toUserEntity(userResponse);
        userRepository.delete(userEntity);
    }


@PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById (Long id){
    log.info("in method Get User by id");
    return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOTFOUND)));


    }
@PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers()
{
    log.info("in method Get User List");
        return userMapper.toListUserResponse(userRepository.findAll());
    }
}
