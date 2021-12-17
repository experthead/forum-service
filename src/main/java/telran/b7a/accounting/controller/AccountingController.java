package telran.b7a.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public UserResponseDto loginUser(@RequestBody CredentionalDto userLogin) {
		return service.loginUser(userLogin.getLogin());
	}

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
	public UserRolesDto deleteRole(@PathVariable String login, @PathVariable String role) {
		return service.deleteRole(login, role);
	}

	@PutMapping("/user/password")
	public void changePassword(@RequestBody CredentionalDto credentionals) {
		service.changePassword(credentionals);
	}

}
