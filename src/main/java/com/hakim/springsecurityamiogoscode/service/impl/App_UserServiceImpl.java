package com.hakim.springsecurityamiogoscode.service.impl;

import com.hakim.springsecurityamiogoscode.exception.ResourceNotFoundException;
import com.hakim.springsecurityamiogoscode.model.App_User;
import com.hakim.springsecurityamiogoscode.repository.App_UserRepository;
import com.hakim.springsecurityamiogoscode.service.App_UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class App_UserServiceImpl implements App_UserService {
    private final App_UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public App_UserServiceImpl(App_UserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

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
}
