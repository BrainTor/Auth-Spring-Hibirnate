package com.example.demo.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserUtills {
	private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public String hashPassword(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public boolean checkPassword(String plainText, String hashedPassword) {
        return passwordEncoder.matches(plainText, hashedPassword);
    }
}
