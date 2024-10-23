package  com.example.demo.services;
import org.springframework.stereotype.Service;

import com.example.demo.Utils.JWT_Util;
import com.example.demo.Utils.UserUtills;
import  com.example.demo.entety.UserEntety;
import  com.example.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	 private final UserRepository userRepository;
	 private final UserUtills userUtills= new UserUtills();
	 private final JWT_Util jwt = new JWT_Util();
	    public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
		 public String login_user(String login, String Password) {
			 UserEntety obj =  userRepository.findByLogin(login);
			 if(obj == null)
				 return "user unregistered";
			 if(!userUtills.checkPassword(Password ,obj.getPassword()))
				 return "wrong password";
			 else 
				 return jwt.generateToken(login);
			
		 }
		@Transactional
		public String register_user(String login, String password, String email ) {
			 UserEntety obj =  userRepository.findByLogin(login);
			 if(obj != null)
				 return "user registred";	 
			 obj = new UserEntety();
			 obj.setEmail(email);
			 try {
				obj.setPassword(userUtills.hashPassword(password));
			} catch (Exception e) {
				e.printStackTrace();
			}
			 obj.setLogin(login);
			 userRepository.save(obj);
			 
			 return jwt.generateToken(login);
		}
	
}
