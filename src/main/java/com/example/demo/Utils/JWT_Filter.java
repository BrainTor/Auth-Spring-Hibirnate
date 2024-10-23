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
    
	

    // ����������� ��� ��������� ������������
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
        
        // ���������� ������ �� ��������� Authorization
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        	
            token = bearerToken.substring(7);
            System.out.println(token);
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (JwtException e) {
                // ��������� ������ JWT (��������, �����������)
                System.out.println("������ JWT: " + e.getMessage());
            }
        }

        // ���� ��� ������������ ������� � �������� ������������ ����
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // �������� ������� ������������
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // �������� ���������� ������
                if (jwtUtil.validateToken(token)) {
                    // �������� ������ ��������������
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // ��������� ������� ��������������
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    
                    // ��������� �������������� � �������� ������������
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (UsernameNotFoundException e) {
                // ��������� ������, ����� ������������ �� ������
                System.out.println("������������ �� ������: " + e.getMessage());
            }
        }

        // �������� ���������� ���������� ������� � �������
        filterChain.doFilter(request, response);
    }
}
