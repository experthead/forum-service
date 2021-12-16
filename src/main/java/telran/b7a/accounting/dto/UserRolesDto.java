package telran.b7a.accounting.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDto {
	String login;
	@Singular
	Set<String> roles;
	   
}
