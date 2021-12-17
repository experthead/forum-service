package telran.b7a.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2424334342957966301L;
	
	public RoleNotFoundException(String role) {
		super("Role " + role + " not found for this user!");
	}

}
