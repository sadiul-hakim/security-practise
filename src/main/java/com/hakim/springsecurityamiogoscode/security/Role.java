package com.hakim.springsecurityamiogoscode.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.hakim.springsecurityamiogoscode.security.Permission.*;

public enum Role {
    STUDENT(new HashSet<>()),
    ADMIN(Set.of(STUDENT_WRITE,STUDENT_READ,COURSE_READ,COURSE_WRITE));
    private final Set<Permission> permissions;
    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    public Set<Permission> getPermissions() {
        return permissions;
    }
    public Set<GrantedAuthority> getGrantedAuthority(){
        Set<GrantedAuthority> grantedAuthorities= getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+name()));

        return grantedAuthorities;
    }
}