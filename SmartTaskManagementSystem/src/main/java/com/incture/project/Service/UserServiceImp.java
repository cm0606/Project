package com.incture.project.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.incture.project.Entity.User;
import com.incture.project.Repo.UserRepo;
import com.incture.project.dto.LoginDto;
import com.incture.project.dto.UserDto;
import com.incture.project.exception.UserAlreadyExistsException;
import com.incture.project.exception.UserNotFoundException;
import com.incture.project.response.LoginMessage;
@Service
public class UserServiceImp implements UserService {
	private static  final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);
	@Autowired 
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
    private AuthenticationManager authenticationManager;

	@Override
	public String registerUser(UserDto userDto) {
		LOGGER.info("Registering new user with username: " + userDto.getUserName());
		try {
			User newUser = new User();
			newUser.setName(userDto.getName());
			newUser.setUserName(userDto.getUserName());
			newUser.setUserEmail(userDto.getUserEmail());
			newUser.setRole(userDto.getRole() != null ? userDto.getRole() : "USER");
			newUser.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
			userRepo.save(newUser);
			LOGGER.info("User successfully created with username: {}", userDto.getUserName());
			return "User Created Successfully";
		}
		catch (DataIntegrityViolationException e){
			LOGGER.error("User with this username or email already exists.", e.getMessage());
			throw new UserAlreadyExistsException("User with this username or email already exists.");
			
		}
		catch (Exception e) {
			LOGGER.error("An error occurred while registering the user.", e.getMessage());
            throw new RuntimeException("An error occurred while registering the user.");
        }
		
			
		
	}

	@Override
	public LoginMessage getUser(LoginDto loginDto) {
		LOGGER.info("Attempting login for username: " + loginDto.getUserName());
		try {
			User user1 = userRepo.findByUserName(loginDto.getUserName());
			if (user1 != null){
				String inputPassword = loginDto.getPassword();
				String encodedPassword = user1.getPassword();
				Boolean isRight = passwordEncoder.matches(inputPassword, encodedPassword);
				if (isRight) {
					LOGGER.info("Password match successful for username: " + loginDto.getUserName());
					Authentication authentication = authenticationManager.authenticate(
				            new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword())
				        );
					if (authentication.isAuthenticated()) {
						LOGGER.info("Login successful");
			            return new LoginMessage("Login Success", true);
			        } else {
			        	LOGGER.warn("Login failed due to authentication issue");
			            return new LoginMessage("Login Failed", false);
			        }
				}
				else {
					LOGGER.warn("Password incorrect for username: " + loginDto.getUserName());
					return new LoginMessage("Password Incorrect", false);
				}
			}
			else {
				LOGGER.warn("User not found");
				return new LoginMessage("User not Found", false);
			}
		}
		catch (Exception e) {
            LOGGER.error("An error occurred during login for username: " + loginDto.getUserName(), e.getMessage());
            return new LoginMessage("Login Failed due to an error", false);
        }
		
	}

	@Override
	public void updateEmail(Long userId, String email) {
		LOGGER.info("Updating email for user with ID: " + userId);
		try {
			User user = userRepo.findById(userId).orElseThrow(()->{
				LOGGER.error("User not found with ID: {}", userId);
				return new UserNotFoundException("User with id: "+ userId + " not found");
			});
			if(email!=null) {
				LOGGER.info("User email updated successfully for user ID: "+ userId);
				user.setUserEmail(email);
				userRepo.save(user);
			}
		}
		catch (Exception e) {
            LOGGER.error("An error occurred while updating email for user ID: " + userId, e.getMessage());
            throw new RuntimeException("An error occurred while updating the email.");
        }
		
	}

	@Override
	public void deleteUser(Long userId) {
		LOGGER.info("Attempting to delete user with ID: {}", userId);
		try {
			User user = userRepo.findById(userId).orElseThrow(()->{
				LOGGER.error("User with id: {} not found", userId);
				return new UserNotFoundException("User with id: "+ userId + " not found");
			});
			userRepo.delete(user);
			LOGGER.info("User with ID: {} successfully deleted", userId);
		}
		catch (Exception e) {
			LOGGER.error("An error occurred while deleting user with ID: " + userId, e.getMessage());
            throw new RuntimeException("An error occurred while deleting the user.");	
		}
	}
}
