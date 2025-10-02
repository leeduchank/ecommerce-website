package spring_api.com.hanhle.myspringbootapp.controllers;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_api.com.hanhle.myspringbootapp.dto.response.ApiResponse;
import spring_api.com.hanhle.myspringbootapp.dto.request.AuthenticationRequest;
import spring_api.com.hanhle.myspringbootapp.dto.response.AuthenticationResponse;
import spring_api.com.hanhle.myspringbootapp.services.AuthenticationService;
import spring_api.com.hanhle.myspringbootapp.services.UserService;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest){
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>();
        if(authenticationService.login(loginRequest)) {
            apiResponse.setCode(200);
            apiResponse.setMessage("Dang nhap thanh coong!!");
        }
        else
        {
            apiResponse.setCode(400);
            apiResponse.setMessage("Ban da nhap sai mat khau!!");
        }
        return apiResponse;
    }
}
