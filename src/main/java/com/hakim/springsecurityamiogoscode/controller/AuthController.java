package com.hakim.springsecurityamiogoscode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping( "/loginPage")
    public String login(){
        return "login";
    }
    @GetMapping( "/registerPage")
    public String register(){
        return "register";
    }
}
