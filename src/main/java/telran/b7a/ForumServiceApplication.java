package telran.b7a;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import telran.b7a.accounting.dao.AccountingMongoDBRepository;
import telran.b7a.accounting.model.User;

@SpringBootApplication
public class ForumServiceApplication implements CommandLineRunner {
	@Autowired
	AccountingMongoDBRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("admin")) {
			String password = BCrypt.hashpw("admin", BCrypt.gensalt());
			User userAccount = new User("admin", password, "", "");
			userAccount.addRole("USER".toUpperCase());
			userAccount.addRole("MODERATOR".toUpperCase());
			userAccount.addRole("ADMINISTRATOR".toUpperCase());
			repository.save(userAccount);
		}
		
	}

}
