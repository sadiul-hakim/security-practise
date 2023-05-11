package com.hakim.springsecurityamiogoscode.repository;

import com.hakim.springsecurityamiogoscode.model.App_User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface App_UserRepository extends JpaRepository<App_User,Long> {
    Optional<App_User> findByEmail(String email);
}
