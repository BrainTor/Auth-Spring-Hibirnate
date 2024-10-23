package com.example.demo.Utils;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.services.CustomUserDetrails;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWT_Filter extends OncePerRequestFilter {

    private final JWT_Util jwtUtil;
    private final CustomUserDetrails userDetailsService;
    
	

    // Конструктор для внедрения зависимостей
    public JWT_Filter(JWT_Util jwtUtil, CustomUserDetrails userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        String token = null;
        String username = null;
        
        // Извлечение токена из заголовка Authorization
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        	
            token = bearerToken.substring(7);
            System.out.println(token);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (JwtException e) {
                // Обработка ошибок JWT (например, логирование)
                System.out.println("Ошибка JWT: " + e.getMessage());
            }
        }

        // Если имя пользователя найдено и контекст безопасности пуст
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Загрузка деталей пользователя
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Проверка валидности токена
                if (jwtUtil.validateToken(token)) {
                    // Создание токена аутентификации
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Установка деталей аутентификации
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // Установка аутентификации в контекст безопасности
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException e) {
                // Обработка случая, когда пользователь не найден
                System.out.println("Пользователь не найден: " + e.getMessage());
            }
        }

        // Передача управления следующему фильтру в цепочке
        filterChain.doFilter(request, response);
    }
}
