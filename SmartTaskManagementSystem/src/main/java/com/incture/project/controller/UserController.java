package com.incture.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.project.Service.UserService;
import com.incture.project.dto.LoginDto;
import com.incture.project.dto.UserDto;
import com.incture.project.response.LoginMessage;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;
	@PostMapping("/signUp")
	public String registerUser(@RequestBody UserDto userDto) {
		return userService.registerUser(userDto);
		
		
	}
	@PostMapping("/login")
	public ResponseEntity<?> getUser(@RequestBody LoginDto loginDto) {
		LoginMessage loginMessage = userService.getUser(loginDto);
		return ResponseEntity.ok(loginMessage);
		
	}
	@PutMapping("/update/{userId}")
	public void updateEmail(@PathVariable Long userId, @RequestBody UserDto userDto) {
		String email = userDto.getUserEmail();
		this.userService.updateEmail(userId,email);
	}
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") Long userId) {
		this.userService.deleteUser(userId);
		
	}

}
