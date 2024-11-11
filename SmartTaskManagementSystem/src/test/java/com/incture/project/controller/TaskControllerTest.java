package com.incture.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
import com.incture.project.Repo.TaskRepo;
import com.incture.project.Repo.UserRepo;
import com.incture.project.Service.TaskService;
import com.incture.project.Service.UserService;
import com.incture.project.dto.TaskDto;
import com.incture.project.dto.UserDto;
@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private TaskRepo taskRepo;
    @InjectMocks 
    private TaskController taskController;

    private TaskDto taskDto;
    private UserDto userDto;
    private Task task;
    private User user;
    
    @BeforeEach
    public void setup() {
        taskDto = new TaskDto();
        taskDto.setTitle("Task");
        taskDto.setDescription("Task Description");
        taskDto.setPriority("High");
        taskDto.setStatus("PENDING");
        
        userDto = new UserDto();
        userDto.setUserName("admin123");
        userDto.setPassword("password123");
        userDto.setUserEmail("admin@gmail.com");

        user = new User();
        user.setId(1L);
        user.setName("Admin");
        user.setRole("ADMIN");
        user.setUserName("admin123");
        user.setUserEmail("admin@gmail.com");

        task = new Task();
        task.setId(1L);
        task.setTitle("Task");
        task.setDescription("Task Description");
        task.setPriority("High");
        task.setStatus("PENDING");
        task.setUser(user); 
    }

	@Test
	void testCreateTask() throws Exception {
		when(taskService.createTask(any(TaskDto.class))).thenReturn("Task created successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isOk()) 
                .andExpect(content().string("Task created successfully"));  

        verify(taskService, times(1)).createTask(any(TaskDto.class));
	}

	@Test
	void testGetTasksByUserId() throws Exception {
		when(taskService.getTaskByUserId(anyLong())).thenReturn(Collections.singletonList(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/1"))
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$", hasSize(1)))  
                .andExpect(jsonPath("$[0].title").value("Task"));  

        verify(taskService, times(1)).getTaskByUserId(anyLong());
	}

	@Test
	void testGetTaskByUserByCriterion() throws  Exception {
		when(taskService.getTasksByTitle(anyString())).thenReturn(Collections.singletonList(task));    
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tasks/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("Task"));  

	        verify(taskService, times(1)).getTasksByTitle(anyString());

	}

	@Test
	void testUpdateTask() throws Exception {
		doNothing().when(taskService).updateTask(any(TaskDto.class), anyLong(), any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/tasks/task/1/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(taskDto)))
                .andExpect(status().isOk());  

        verify(taskService, times(1)).updateTask(any(TaskDto.class), eq(1L), eq(user));
	}

	@Test
	void testDeleteTask() throws Exception {
		doNothing().when(taskService).deleteTask(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tasks/task/1/1"))
                .andExpect(status().isOk());
        
        verify(taskService, times(1)).deleteTask(anyLong());
	}

}
