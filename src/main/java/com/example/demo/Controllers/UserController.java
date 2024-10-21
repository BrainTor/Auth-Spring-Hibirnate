package com.example.demo.Controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.UserService;

import  com.example.demo.Object.Auth.Login;
import com.example.demo.Object.Auth.Register;


@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService; 
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/login")
	private ResponseEntity<String> login_func(@RequestBody Login login){
		String token = userService.login_user(login.getLogin(), login.getPassword());
		System.out.println(token);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	@PostMapping("/reg")
	private ResponseEntity<String> reg_func(@RequestBody Register register){
		String token = userService.register_user(register.getLogin(), register.getPassword(), register.getEmail());
		System.out.println(token);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

}
