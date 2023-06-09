package com.hakim.springsecurityamiogoscode.payload;

import com.hakim.springsecurityamiogoscode.model.App_User;
import com.hakim.springsecurityamiogoscode.repository.App_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("custom")
public class CustomUserDetailService implements UserDetailsService {
    private final App_UserRepository appUserRepository;

    @Autowired
    public CustomUserDetailService(App_UserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        App_User user = appUserRepository.findByEmail(username);

        return new CustomUserDetails(
                user.getRole().getGrantedAuthority(),
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true
        );
    }
}
