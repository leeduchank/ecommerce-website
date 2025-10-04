package spring_api.com.hanhle.myspringbootapp.controllers;


import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_api.com.hanhle.myspringbootapp.dto.request.IntrospectRequest;
import spring_api.com.hanhle.myspringbootapp.dto.response.ApiResponse;
import spring_api.com.hanhle.myspringbootapp.dto.request.AuthenticationRequest;
import spring_api.com.hanhle.myspringbootapp.dto.response.AuthenticationResponse;
import spring_api.com.hanhle.myspringbootapp.dto.response.IntrospectResponse;
import spring_api.com.hanhle.myspringbootapp.services.AuthenticationService;
import spring_api.com.hanhle.myspringbootapp.services.UserService;

import java.text.ParseException;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest){

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .result(authenticationService.login(loginRequest))
                .build()
        ;
    }

    @PostMapping("/intropect")
    public ApiResponse<IntrospectResponse> authenicate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {

        IntrospectResponse introspectResponse = authenticationService.Introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(introspectResponse)
                .build();
    }
}
