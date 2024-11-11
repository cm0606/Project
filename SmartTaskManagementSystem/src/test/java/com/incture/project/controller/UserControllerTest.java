package com.incture.project.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.project.Service.UserService;
import com.incture.project.dto.LoginDto;
import com.incture.project.dto.UserDto;
import com.incture.project.response.LoginMessage;
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; 

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    private UserDto userDto;
    private LoginDto loginDto;
    
    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();

        userDto = new UserDto();
        userDto.setName("User");
        userDto.setUserName("user123");
        userDto.setUserEmail("user@gmail.com");
        userDto.setPassword("password123");

        loginDto = new LoginDto();
        loginDto.setUserName("user123");
        loginDto.setPassword("password123");

    }

	@Test
	void testRegisterUser() throws Exception {
		when(userService.registerUser(any(UserDto.class))).thenReturn("User Created Successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Created Successfully"));

        verify(userService, Mockito.times(1)).registerUser(any(UserDto.class));
	}

	@Test
	void testGetUser() throws Exception {
		LoginMessage loginMessage = new LoginMessage("Login Success", true);
	    when(userService.getUser(any(LoginDto.class))).thenReturn(loginMessage);
	    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user/login")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(loginDto)))
	            .andExpect(status().isOk())  
	            .andExpect(jsonPath("$.message").value("Login Success"))  
	            .andExpect(jsonPath("$.status").value(true)); 

	    verify(userService, Mockito.times(1)).getUser(any(LoginDto.class));
	}

	@Test
	void testUpdateEmail() throws  Exception {
		String newEmail = "newmail@gmail.com";
        userDto.setUserEmail(newEmail);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/user/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        verify(userService, Mockito.times(1)).updateEmail(anyLong(), any(String.class));
	}

	@Test
	void testDeleteUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/user/1"))
        	.andExpect(status().isOk());

		verify(userService, Mockito.times(1)).deleteUser(anyLong());
	}

}
