package spring_api.com.hanhle.myspringbootapp.controllers;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import spring_api.com.hanhle.myspringbootapp.dto.response.ApiResponse;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
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
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setMessage("Tao thanh cong");
        apiResponse.setResult(userService.register(userDto));
        return apiResponse;
    }



    @PutMapping("/{id}")
    public ApiResponse<UserDto> updateUserByID(@PathVariable("id") Long id,@RequestBody UserDto userDto){
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Da thay doi thanh cong username voi id " + id);
        apiResponse.setResult(userService.update(id,userDto));
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
