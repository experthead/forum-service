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

	@DeleteMapping("/user/{user}")
	public UserResponseDto deleteUser(@PathVariable String userName) {
		return service.deleteUser(userName);
	}

	@PutMapping("/user/{user}")
	public UserResponseDto updateUser(@PathVariable String userName, @RequestBody UserUpdateDto user) {
		return service.updateUser(userName, user);
	}

	@PutMapping("/user/{user}/role/{role}")
	public UserRolesDto addRole(@PathVariable String userName, @PathVariable String role) {
		return service.addRole(userName, role);
	}

	@DeleteMapping("/user/{user}/role/{role}")
	public UserRolesDto deleteRole(@PathVariable String userName, @PathVariable String role) {
		return service.deleteRole(userName, role);
	}

	@PutMapping("/user/password")
	public void changePassword(@RequestBody CredentionalDto credentionals) {
		service.changePassword(credentionals);
	}

}
