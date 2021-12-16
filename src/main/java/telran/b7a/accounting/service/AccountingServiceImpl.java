package telran.b7a.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import telran.b7a.accounting.dao.AccountingMongoDBRepository;
import telran.b7a.accounting.dto.CredentionalDto;
import telran.b7a.accounting.dto.UserRegisterDto;
import telran.b7a.accounting.dto.UserResponseDto;
import telran.b7a.accounting.dto.UserRolesDto;
import telran.b7a.accounting.dto.UserUpdateDto;
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
		accountsRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto loginUser(String login) {
		User user = accountsRepository.findById(login).orElseThrow(() -> new UserNotFoundException());
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto deleteUser(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto updateUser(String userName, UserUpdateDto user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRolesDto addRole(String userName, String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRolesDto deleteRole(String userName, String role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(CredentionalDto credentionals) {
		// TODO Auto-generated method stub

	}

}
