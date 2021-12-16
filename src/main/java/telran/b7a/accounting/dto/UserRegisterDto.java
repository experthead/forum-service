package telran.b7a.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterDto {
	String login;
	String password;
	String firstName;
	String lastName;
}
