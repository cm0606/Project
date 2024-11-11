package com.incture.project.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
import com.incture.project.Repo.TaskRepo;
import com.incture.project.Repo.UserRepo;
import com.incture.project.Service.EmailService;
import com.incture.project.dto.Email;
@SpringBootTest
@AutoConfigureMockMvc
class SendReminderControllerTest {
	@Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService emailService;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private TaskRepo taskRepo;
    @InjectMocks
    private SendReminderController sendReminderController;
    private List<Task> tasks;
    private User admin;
  
    private Task task;
    @BeforeEach
    void setUp() {
    	admin = new User();
        admin.setId(1L);
        admin.setUserEmail("admin@example.com");
        admin.setName("Admin");
        admin.setRole("ADMIN");

        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDeadline(LocalDate.now().plusDays(2));
        User newUser = new User();
        newUser.setId(2L);
        newUser.setUserEmail("user@example.com");
        newUser.setName("User");
        newUser.setRole("USER");
        task.setUser(newUser);
        
        tasks = new ArrayList<>();
        tasks.add(task);
    }

	@Test
	void testSendEmail() throws Exception {
		when(userRepo.findById(anyLong())).thenReturn(Optional.of(admin));
        when(taskRepo.findAllByDate(any(LocalDate.class))).thenReturn(tasks);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks/sendemail/1"))
	        .andExpect(status().isOk())
	        .andExpect(content().string("Send"));
        Mockito.verify(emailService, Mockito.times(1)).sendSimpleMail(Mockito.any(Email.class), Mockito.anyString());
	}
	

}
