package com.incture.project.Service;


import com.incture.project.dto.LoginDto;
import com.incture.project.dto.UserDto;
import com.incture.project.response.LoginMessage;

public interface UserService {

	String registerUser(UserDto userDto);

	LoginMessage getUser(LoginDto loginDto);

	void updateEmail(Long userId, String email);

	void deleteUser(Long userId);


	

}
