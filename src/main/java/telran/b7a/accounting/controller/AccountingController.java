package telran.b7a.accounting.controller;

import java.security.Principal;
import java.util.Base64;

import javax.management.relation.RoleInfoNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.b7a.accounting.dto.CredentionalDto;
import telran.b7a.accounting.dto.UserUpdateDto;
import telran.b7a.accounting.dto.UserResponseDto;
import telran.b7a.accounting.dto.UserRolesDto;
import telran.b7a.accounting.dto.UserRegisterDto;
import telran.b7a.accounting.service.AccountingService;

@RestController
@RequestMapping("/account")
public class AccountingController {

	AccountingService service;

	@Autowired
	public AccountingController(AccountingService service) {
		this.service = service;
	}

	@PostMapping("/register")
	public UserResponseDto addUser(@RequestBody UserRegisterDto newUser) {
		return service.registerNewUser(newUser);
	}
	
	@PostMapping("/login")
	public UserResponseDto loginUser(Principal principal) {
		return service.loginUser(principal.getName());
	}
	
//	@PostMapping("/login")
//	public UserResponseDto loginUser(@RequestHeader("Authorization") String token) {
//		token = token.split(" ")[1];
//		byte[] bytesDecode = Base64.getDecoder().decode(token);
//		token = new String(bytesDecode);
//		String[] credentionals = token.split(":");
//		return service.loginUser(credentionals[0]);
//	}

	@DeleteMapping("/user/{login}")
	public UserResponseDto deleteUser(@PathVariable String login) {
		return service.deleteUser(login);
	}

	@PutMapping("/user/{login}")
	public UserResponseDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto user) {
		return service.updateUser(login, user);
	}

	@PutMapping("/user/{login}/role/{role}")
	public UserRolesDto addRole(@PathVariable String login, @PathVariable String role) {
		return service.addRole(login, role);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public UserRolesDto deleteRole(@PathVariable String login, @PathVariable String role) throws RoleInfoNotFoundException {
		return service.deleteRole(login, role);
	}

	@PutMapping("/user/password")  //change to "/user/password"
	public void changePassword(@RequestBody CredentionalDto credentionals) {
		service.changePassword(credentionals);
	}

}
