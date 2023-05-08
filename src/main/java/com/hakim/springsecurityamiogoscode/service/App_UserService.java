package com.hakim.springsecurityamiogoscode.service;

import com.hakim.springsecurityamiogoscode.model.App_User;
import com.hakim.springsecurityamiogoscode.payload.LoginRequest;

import java.util.List;

public interface App_UserService {
    App_User saveAppUser(App_User appUser);
    void deleteAppUser(long id);
    App_User getAppUser(long id);
    List<App_User> getAppUserList();

    String authenticate(LoginRequest request);
}
