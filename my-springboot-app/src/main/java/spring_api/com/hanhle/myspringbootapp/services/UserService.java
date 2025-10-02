package spring_api.com.hanhle.myspringbootapp.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring_api.com.hanhle.myspringbootapp.dto.request.AuthenticationRequest;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.exception.AppException;
import spring_api.com.hanhle.myspringbootapp.exception.ErrorCode;
import spring_api.com.hanhle.myspringbootapp.mapper.UserMapper;
import spring_api.com.hanhle.myspringbootapp.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE  ,makeFinal = true)
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    public UserDto register (UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity user = userMapper.toUserEntity(userDto);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userMapper.toUserDto(userRepository.save(user));
    };

    public UserDto update(Long id,UserDto userDto){
        UserEntity userEntity = userMapper.toUserEntity(getUserById(id));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userMapper.updateUser(userEntity,userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return (userMapper.toUserDto(userRepository.save(userEntity)));
    }




    public void delete(Long id){
        UserDto userDto = getUserById(id);
        UserEntity userEntity = userMapper.toUserEntity(userDto);
        userRepository.delete(userEntity);
    }

    public UserDto getUserById (Long id){
        return userMapper.toUserDto(userRepository.findById(id)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOTFOUND)));


    }

    public List<UserDto> getAllUsers(){
        return userMapper.toListUserDto(userRepository.findAll());
    }
}
