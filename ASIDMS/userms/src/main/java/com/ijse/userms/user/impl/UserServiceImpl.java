package com.ijse.userms.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ijse.userms.user.User;
import com.ijse.userms.user.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username){

        User user = userRepository.findByUsername(username).orElse(null);


        if(user == null){

            throw new UsernameNotFoundException("User not found with the given username");
        }


        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .build();
    }
}
