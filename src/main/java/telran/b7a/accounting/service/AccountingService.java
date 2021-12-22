package telran.b7a.accounting.service;

import telran.b7a.accounting.dto.CredentionalDto;
import telran.b7a.accounting.dto.UserResponseDto;
import telran.b7a.accounting.dto.UserRegisterDto;
import telran.b7a.accounting.dto.UserUpdateDto;
import telran.b7a.accounting.dto.UserRolesDto;

public interface AccountingService {
	UserResponseDto registerNewUser(UserRegisterDto newUser);

	UserResponseDto loginUser(String login);

	UserResponseDto deleteUser(String userName);

	UserResponseDto updateUser(String userName, UserUpdateDto user);

	UserRolesDto addRole(String userName, String role);

	UserRolesDto deleteRole(String userName, String role);

	void changePassword(String name, String password);

}
