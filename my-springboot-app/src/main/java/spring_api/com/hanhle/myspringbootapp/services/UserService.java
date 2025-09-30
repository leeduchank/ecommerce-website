package spring_api.com.hanhle.myspringbootapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring_api.com.hanhle.myspringbootapp.dto.LoginRequest;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.exception.AppException;
import spring_api.com.hanhle.myspringbootapp.exception.ErrorCode;
import spring_api.com.hanhle.myspringbootapp.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity register (UserDto userDto){
        if (userRepository.existsByUsername(userDto.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity user = new UserEntity();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());





        return userRepository.save(user);


    };


    public boolean login(LoginRequest loginRequest)
    {

        return userRepository.findByUsername(loginRequest.getUsername())
                .map(user->user.getPassword().equals(loginRequest.getPassword()))
                .orElse(false);
    }

    public UserEntity getUserById (Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id: " + id));
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
}
