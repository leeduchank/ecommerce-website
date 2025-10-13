package spring_api.com.hanhle.myspringbootapp.controllers;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring_api.com.hanhle.myspringbootapp.dto.response.ApiResponse;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
import spring_api.com.hanhle.myspringbootapp.dto.response.UserResponse;
import spring_api.com.hanhle.myspringbootapp.services.UserService;

import java.util.List;

@RestController
@RequestMapping ("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;


    @PostMapping("")
    public ApiResponse<UserResponse> register(@RequestBody @Valid UserDto userDto){

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Create user successfully")
                .result(userService.register(userDto))
                .build();
    }



    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUserByID(@PathVariable("id") Long id,@RequestBody UserDto userDto){

        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("Update successfully")
                .result(userService.update(id,userDto))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUserById(@PathVariable ("id") Long id){
        userService.delete(id);
        return ApiResponse.<String>builder()
                .code(200)
                .result("Delete user successfully")
                .build();
    }
    @GetMapping( "/{id}")
    public ApiResponse <UserResponse> getUserById(@PathVariable("id") Long id){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.getUserById(id))
                .build();
    }
    @GetMapping( "myInfo")
    public ApiResponse <UserResponse> getUserInfo(){
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.userGetInfo())
                .build();
    }

    @GetMapping("")
    public ApiResponse <List<UserResponse>> getAllUser() {
        var authenication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}" , authenication.getName());
        authenication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        List<UserResponse> listUser = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .result(listUser)
                .build();
    }
}
