package com.bridgelabz.userservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.userservice.dto.LoginDTO;
import com.bridgelabz.userservice.dto.UserDTO;
import com.bridgelabz.userservice.entity.User;
import com.bridgelabz.userservice.exception.FundooException;
import com.bridgelabz.userservice.repo.UserRepository;
import com.bridgelabz.userservice.utils.TokenService;


@Service
public class UserServiceImp implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	
	@Override
	public void register(UserDTO userDto) {
		Optional<User> isUserPresent = userRepository.findByEmailId(userDto.getEmailId());
		
		if(isUserPresent.isPresent()) {
			throw new FundooException(HttpStatus.CONFLICT.value(),"Email is already present");
		}
		
		//can use model mapper or constructor to inject data
		User user = new User();
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		BeanUtils.copyProperties(userDto, user);
		user.setIsVerified(true);
		User savedUser = userRepository.save(user);
//		boolean isEmailSent = emailService.sendMail("Verify Your Email", "http://localhost:8081/user/verify/"+tokenService.createToken(savedUser.getId()), savedUser.getEmailId(), "venakattestmail@gmail.com");
//		if(!isEmailSent)
//			throw new FundooException(HttpStatus.BAD_REQUEST.value(), "User is registered but error while sending email verification link");
	}

	
	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}


	@Override
	public void verifyUser(String token) {
		Long userId = tokenService.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(
				                          () -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		user.setIsVerified(true);
		userRepository.save(user);
	}


	@Override
	public String login(LoginDTO loginDto) {
		User user =  getUser(loginDto.getEmailId());		
		if(!user.getIsVerified()) {
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "Email is not verified");
		}
		
		if(user.getEmailId().equals(loginDto.getEmailId()) && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			return tokenService.createToken(user.getId());
		}else {
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "Email or password is in correct");
		}
		
	}


	

	@Override
	public void restPassword(String password, String token) {
			Long userId = tokenService.decodeToken(token);
			User user = getUser(userId);
			int rowsAffected = userRepository.updatePassword(passwordEncoder.encode(password), user.getId());
			System.out.println(rowsAffected);
			//user.setPassword(passwordEncoder.encode(password));
			//userRepository.save(user);
	}
	
	public User getUser(String emailId) {
		return userRepository.findByEmailId(emailId).orElseThrow(
                () -> new FundooException(HttpStatus.NOT_FOUND.value(), "Email is Not Registered"));
	}
	
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
                () -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
	}


	@Override
	public Long getUserId(String token) {
		return getUser(tokenService.decodeToken(token)).getId();
	}


	}
