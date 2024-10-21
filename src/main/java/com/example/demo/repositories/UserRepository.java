package  com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import  com.example.demo.entety.UserEntety;
@Repository
public interface UserRepository extends JpaRepository <UserEntety, Long> {
	UserEntety findByLogin(String login);
}
