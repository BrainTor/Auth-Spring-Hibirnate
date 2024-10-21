package  com.example.demo.entety;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class UserEntety {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public UserEntety() {
		
	}
	@Column(unique = true)
	private String login;
	private String password; 
	private String email; 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	} 
	
	

}
