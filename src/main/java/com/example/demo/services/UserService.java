package  com.example.demo.services;
import org.springframework.stereotype.Service;
import  com.example.demo.entety.UserEntety;
import  com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	 private final UserRepository userRepository;

	    public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
		 public String login_user(String login, String Password) {
			 UserEntety obj =  userRepository.findByLogin(login);
			 if(obj == null)
				 return "user unregistered";
			 if(!obj.getPassword().equals(Password))
				 return "wrong password";
			 else 
				 return "token";
		 }
		@Transactional
		public String register_user(String login, String password, String email ) {
			 UserEntety obj =  userRepository.findByLogin(login);
			 if(obj != null)
				 return "user registred";
			 obj = new UserEntety();
			 obj.setEmail(email);
			 obj.setPassword(password);
			 obj.setLogin(login);
			 userRepository.save(obj);
			 return "done";
		}
	
}
