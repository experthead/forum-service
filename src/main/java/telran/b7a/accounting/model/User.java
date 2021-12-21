package telran.b7a.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;


@Getter
@Setter
@EqualsAndHashCode(of = { "login" })
@ToString
@Document(collection = "users")

public class User {

	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	@Singular
	Set<String> roles;
	
	
	
	public User() {
		roles = new HashSet<>();
	}

	public boolean addRole(String role) {
		return roles.add(role);
		
	}

	public boolean deleteRole(String role) {
		return roles.remove(role);
		
	}

}
