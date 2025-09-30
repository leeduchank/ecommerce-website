package spring_api.com.hanhle.myspringbootapp.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserDto> register(@RequestBody @Valid UserDto userDto){
        userService.register(userDto);
        ApiResponse<UserDto> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userDto);
        return apiResponse;
    }


    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        if(userService.login(loginRequest))
            return ResponseEntity.ok("Dang nhap duoc roai!!");
        else
            return ResponseEntity.status(401).body("Ko dc roai!!");
    }

    @GetMapping( "/{id}")
    public ResponseEntity <UserEntity> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/")
    public ResponseEntity <List<UserEntity>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
