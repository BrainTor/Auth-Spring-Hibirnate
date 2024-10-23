package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entety.UserEntety;
import com.example.demo.repositories.UserRepository;
@Service
public class CustomUserDetrails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntety user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден: " + username);
        }
        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles("USER") 
                .build();
    }
}
