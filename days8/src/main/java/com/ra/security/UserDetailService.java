package com.ra.security;

import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        UserPrinciple userPrinciple = new UserPrinciple();
        userPrinciple.setUser(user);
        userPrinciple.setAuthorities(user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet()));

//        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
//        Set<Role> roles = user.getRoles();
//        for (Role account_Role : roles) {
//            grantedAuthoritySet.add(new SimpleGrantedAuthority(account_Role.getName()));
//        }
        return userPrinciple;
    }
}
