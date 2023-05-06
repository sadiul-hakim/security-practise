package com.hakim.springsecurityamiogoscode.model;

import com.hakim.springsecurityamiogoscode.security.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App_User {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;
}
