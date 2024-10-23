package com.example.demo.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	@Autowired
	AuthenticationManager manager;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/login")
	private ResponseEntity<String> login_func(@RequestBody Login login){
		String token = userService.login_user(login.getLogin(), login.getPassword());
		if(token.equals("user unregistered"))
			return new ResponseEntity<String>(token, HttpStatus.FORBIDDEN);
		else if(token.equals("wrong password")) 
			return new ResponseEntity<String>(token, HttpStatus.FORBIDDEN);
		manager.authenticate( new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword()));
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
	@PostMapping("/reg")
	private ResponseEntity<String> reg_func(@RequestBody Register register){
		String token = userService.register_user(register.getLogin(), register.getPassword(), register.getEmail());
		if(token.equals("user registred"))
			return new ResponseEntity<String>("user registred", HttpStatus.FORBIDDEN);
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}
	
}
