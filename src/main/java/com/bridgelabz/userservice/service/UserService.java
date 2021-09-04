package com.bridgelabz.userservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.userservice.dto.LoginDTO;
import com.bridgelabz.userservice.dto.UserDTO;
import com.bridgelabz.userservice.entity.User;


public interface UserService {
	
    public void register(UserDTO userDto);
	
	public void verifyUser(String token);
	
	public List<User> getAllUsers();
	
	public String login(LoginDTO loginDto);
		
	public void restPassword(String password,String token);
	
	public Long getUserId(String token);
	
}
