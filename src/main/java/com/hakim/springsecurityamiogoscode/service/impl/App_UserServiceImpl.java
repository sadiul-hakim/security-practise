package com.hakim.springsecurityamiogoscode.service.impl;

import com.hakim.springsecurityamiogoscode.exception.ResourceNotFoundException;
import com.hakim.springsecurityamiogoscode.model.App_User;
import com.hakim.springsecurityamiogoscode.payload.LoginRequest;
import com.hakim.springsecurityamiogoscode.repository.App_UserRepository;
import com.hakim.springsecurityamiogoscode.service.App_UserService;
import com.hakim.springsecurityamiogoscode.utility.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class App_UserServiceImpl implements App_UserService {
    private final App_UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Override
    public App_User saveAppUser(App_User appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteAppUser(long id) {
        App_User appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not fount with id: " + id));
        appUserRepository.delete(appUser);
    }

    @Override
    public App_User getAppUser(long id) {
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not fount with id: " + id));
    }

    @Override
    public List<App_User> getAppUserList() {
        PageRequest page = PageRequest.of(1, 50);
        return appUserRepository.findAll(page).toList();
    }

    @Override
    public String authenticate(LoginRequest request) {

        // authenticate() will validate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // If program can reach this here than means provided credentials are correct
        var user = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Could not find user with email: " + request.getEmail()));
        return jwtHelper.generateToken(user);
    }
}
