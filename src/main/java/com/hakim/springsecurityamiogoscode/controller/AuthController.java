package com.hakim.springsecurityamiogoscode.controller;

import com.hakim.springsecurityamiogoscode.model.App_User;
import com.hakim.springsecurityamiogoscode.payload.ApiResponse;
import com.hakim.springsecurityamiogoscode.payload.AuthenticationResponse;
import com.hakim.springsecurityamiogoscode.payload.LoginRequest;
import com.hakim.springsecurityamiogoscode.payload.RegisterRequest;
import com.hakim.springsecurityamiogoscode.security.Role;
import com.hakim.springsecurityamiogoscode.service.App_UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final ModelMapper mapper;
    private final App_UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterRequest request){
        App_User user = mapper.map(request, App_User.class);
        user.setRole(Role.STUDENT);
        App_User appUser = userService.saveAppUser(user);

        return ResponseEntity.ok(new ApiResponse("User Registered successfully!",true));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody LoginRequest request){
        String token=userService.authenticate(request);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
