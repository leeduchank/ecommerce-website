package spring_api.com.hanhle.myspringbootapp.controllers;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.control.MappingControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring_api.com.hanhle.myspringbootapp.dto.ApiResponse;
import spring_api.com.hanhle.myspringbootapp.dto.LoginRequest;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import spring_api.com.hanhle.myspringbootapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping ("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;


    @PostMapping("/register")
    public ApiResponse<UserDto> register(@RequestBody @Valid UserDto userDto){
        userService.register(userDto);
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setMessage("Tao thanh cong");
        apiResponse.setResult(userDto);
        return apiResponse;
    }


    @PostMapping("login")
    public ApiResponse  <String> login(@RequestBody LoginRequest loginRequest){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        if(userService.login(loginRequest)) {
            apiResponse.setCode(200);
            apiResponse.setResult("Dang nhap thanh coong!!");
        }
        else
        {
            apiResponse.setCode(400);
            apiResponse.setResult("Dang nhap khong thanh coong!!");
        }
        return apiResponse;
    }
    @PutMapping("/{id}")
    public ApiResponse updateUserByID(@PathVariable("id") Long id,@RequestBody UserDto userDto){
        userService.update(id,userDto);
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Da thay doi thanh cong username voi id " + id);
        apiResponse.setResult(userDto);
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUserById(@PathVariable ("id") Long id){
        userService.delete(id);
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Delete user succes");
        return apiResponse;
    }
    @GetMapping( "/{id}")
    public ApiResponse <UserDto> getUserById(@PathVariable("id") Long id){
        UserDto userDto = userService.getUserById(id);
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(userDto);
        return apiResponse;
    }

    @GetMapping("/")
    public ApiResponse <List<UserDto>> getAllUser() {
        List<UserDto> listUser = userService.getAllUsers();
        ApiResponse<List<UserDto>> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setResult(listUser);
        return apiResponse;
    }
}
