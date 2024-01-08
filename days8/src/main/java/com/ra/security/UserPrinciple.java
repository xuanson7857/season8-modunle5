package com.ra.security;

import com.ra.model.entity.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserPrinciple implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() { // han su dung cua tai khoan
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // da bi khoa
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // trung chi cua tai khoan
        return true;
    }

    @Override
    public boolean isEnabled() {  // trang thai mo khoa
        return user.isStatus();
    }
}
