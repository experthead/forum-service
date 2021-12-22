package telran.b7a.accounting.service;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import telran.b7a.accounting.dao.AccountingMongoDBRepository;
import telran.b7a.accounting.dto.CredentionalDto;
import telran.b7a.accounting.dto.UserRegisterDto;
import telran.b7a.accounting.dto.UserResponseDto;
import telran.b7a.accounting.dto.UserRolesDto;
import telran.b7a.accounting.dto.UserUpdateDto;
import telran.b7a.accounting.dto.exceptions.RoleNotFoundException;
import telran.b7a.accounting.dto.exceptions.UserAlreadyExistsException;
import telran.b7a.accounting.dto.exceptions.UserNotFoundException;
import telran.b7a.accounting.model.User;

@Service
public class AccountingServiceImpl implements AccountingService {

	AccountingMongoDBRepository accountsRepository;
	ModelMapper modelMapper;

	@Autowired
	public AccountingServiceImpl(AccountingMongoDBRepository accountsRepository, ModelMapper modelMapper) {
		this.accountsRepository = accountsRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public UserResponseDto registerNewUser(UserRegisterDto newUser) {
		if (accountsRepository.existsById(newUser.getLogin())) {
			throw new UserAlreadyExistsException();
		}
		User user = modelMapper.map(newUser, User.class);
		user.addRole("USER".toUpperCase());
		String password = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
		user.setPassword(password);
		accountsRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto loginUser(String login) {
		User user = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto deleteUser(String login) {
		User user = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		accountsRepository.deleteById(login);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto updateUser(String login, UserUpdateDto newUserData) {
		User user = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());

		if (!(newUserData.getFirstName() == null || newUserData.getFirstName().isEmpty()
				|| newUserData.getFirstName().trim().isEmpty())) {
			user.setFirstName(newUserData.getFirstName().trim());
		}
		if (!(newUserData.getLastName() == null || newUserData.getLastName().isEmpty()
				|| newUserData.getLastName().trim().isEmpty())) {
			user.setLastName(newUserData.getLastName().trim());
		}
		accountsRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserRolesDto addRole(String login, String role) {
		User user = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		if (!(role == null || role.isEmpty() || role.trim().isEmpty())) {
			user.addRole(role.toUpperCase().trim());
		}
		accountsRepository.save(user);
		return modelMapper.map(user, UserRolesDto.class);
	}

	@Override
	public UserRolesDto deleteRole(String login, String role) {
		User user = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		if (!user.deleteRole(role.toUpperCase())) {
			throw new RoleNotFoundException(role.toUpperCase());
		}
		accountsRepository.save(user);
		return modelMapper.map(user, UserRolesDto.class);
	}

	@Override
	public void changePassword(String login, String password) {
		User userAccount = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		userAccount.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		accountsRepository.save(userAccount);
	}

	

	

}
