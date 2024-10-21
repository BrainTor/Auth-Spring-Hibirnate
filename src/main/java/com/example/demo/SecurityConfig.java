package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		try {
			http
	         .cors()  
	         .and()
	         .csrf().disable()  // Отключаем CSRF
	         .authorizeHttpRequests(auth -> auth
	             .requestMatchers("/api/**").permitAll()  
	             .anyRequest().authenticated()  
	         );

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		 
     return http.build();
    }
}
