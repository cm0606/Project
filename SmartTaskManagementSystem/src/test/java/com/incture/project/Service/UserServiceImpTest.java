package com.incture.project.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.incture.project.Entity.User;
import com.incture.project.Repo.UserRepo;
import com.incture.project.dto.LoginDto;
import com.incture.project.dto.UserDto;
import com.incture.project.exception.UserAlreadyExistsException;
import com.incture.project.response.LoginMessage;
@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {
	@Mock
	private UserRepo userRepo;
	
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;
    
	@InjectMocks
	private UserServiceImp userService;
	private UserDto userDto;
    private User user;
    private LoginDto loginDto;
    private LoginMessage loginMessage;
	
	private List<User> users;
    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setName("Rohit Mangal");
        userDto.setUserName("rohit12");
        userDto.setUserEmail("rohitmangal12@gmail.com");
        userDto.setPassword("Rm12062002!2");

        user = new User();
        user.setId(1L);
        user.setName("Rohit Mangal");
        user.setUserName("rohit12");
        user.setUserEmail("rohitmangal12@gmail.com");
        user.setPassword("$2a$10$sdi3hqtl9NQHj67WrqQdMOhfVbzPWF38gfCMYWXw25EhLXjJ/8su6");

        loginDto = new LoginDto();
        loginDto.setUserName("rohit12");
        loginDto.setPassword("Rm12062002!2");

        loginMessage = new LoginMessage("Login Success", true);
    }

	@Test
	void testRegisterUserSuccess() {
		when(userRepo.save(any(User.class))).thenReturn(user);
		when(passwordEncoder.encode(userDto.getPassword())).thenReturn("$2a$10$sdi3hqtl9NQHj67WrqQdMOhfVbzPWF38gfCMYWXw25EhLXjJ/8su6");
		String result = userService.registerUser(userDto);
		assertEquals("User Created Successfully", result);
	    verify(userRepo).save(any(User.class));
	}
	

	@Test
	void testGetUserSuccess() {
		when(userRepo.findByUserName(loginDto.getUserName())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        LoginMessage result = userService.getUser(loginDto);

        assertEquals("Login Success", result.getMessage());
        assertTrue(result.getStatus());
	}
	@Test
    void testGetUserUserNotFound() {
    
        when(userRepo.findByUserName(loginDto.getUserName())).thenReturn(null);

        LoginMessage result = userService.getUser(loginDto);

        assertEquals("User not Found", result.getMessage());
        assertFalse(result.getStatus());
    }
	
    @Test
    void testGetUserIncorrectPassword() {
        when(userRepo.findByUserName(loginDto.getUserName())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        LoginMessage result = userService.getUser(loginDto);

        assertEquals("Password Incorrect", result.getMessage());
        assertFalse(result.getStatus());
    }
	

	@Test
	void testUpdateEmailSuccess() {
		String newEmail = "newemail@gmail.com";
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        userService.updateEmail(1L, newEmail);

        assertEquals(newEmail, user.getUserEmail());
        verify(userRepo).save(user);
	}

	@Test
    void testDeleteUserSuccess() {

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepo).delete(user);
    }

}
