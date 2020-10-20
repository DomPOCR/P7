package com.nnk.springboot.services;

import com.nnk.springboot.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MyUserDetailService implements UserDetailsService  {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        if (userName.trim().isEmpty()) {
            throw new UsernameNotFoundException("username is empty");
        }

        com.nnk.springboot.domain.User user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}
