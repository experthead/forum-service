package telran.b7a.accounting.dto;

import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDto {
	String login;
	String firstName;
	String lastName;
	@Singular
	Set<String> roles;
}
