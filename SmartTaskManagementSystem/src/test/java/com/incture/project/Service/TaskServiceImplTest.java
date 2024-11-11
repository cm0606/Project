package com.incture.project.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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

import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
import com.incture.project.Repo.TaskRepo;
import com.incture.project.Repo.UserRepo;
import com.incture.project.dto.Email;
import com.incture.project.dto.TaskDto;
@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
	@Mock
    private UserRepo userRepo;

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User user;
    private Task task;
    private TaskDto taskDto;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUserName("rohit123");
        user.setUserEmail("rohitmangal@gmail.com");

        task = new Task();
        task.setId(1L);
        task.setTitle("Task");
        task.setDescription("Description");
        task.setPriority("High");
        task.setUser(user);

        taskDto = new TaskDto();
        taskDto.setTitle("Task");
        taskDto.setDescription("Description");
        taskDto.setPriority("High");
        taskDto.setUserId(1L);
    }

	@Test
	void testCreateTask() {
		when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
		
		when(taskRepo.save(any(Task.class))).thenReturn(task);

        String result = taskService.createTask(taskDto);

        assertEquals("Task Created Successfully", result);
        
        verify(taskRepo).save(any(Task.class));
	}

	@Test
	void testGetTaskByUserId() {
        when(taskRepo.findByUserId(anyLong())).thenReturn(List.of(task));

        List<Task> tasks = taskService.getTaskByUserId(1L);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepo).findByUserId(anyLong());
	}

	@Test
	void testGetTasksByTitle() {
		when(taskRepo.findAllByTitle(anyString())).thenReturn(List.of(task));

        List<Task> tasks = taskService.getTasksByTitle("Task");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepo).findAllByTitle(anyString());
	}

	@Test
	void testGetTasksByDescription() {
		when(taskRepo.findAllByDescription(anyString())).thenReturn(List.of(task));

        List<Task> tasks = taskService.getTasksByDescription("Description");

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepo).findAllByDescription(anyString());
	}

	

	@Test
	void testGetTasksByUserByTitle() {
		when(taskRepo.findAllByUserByTitle(anyString(), anyLong())).thenReturn(List.of(task));
		List<Task> tasks = taskService.getTasksByUserByTitle("Task",user);
		assertNotNull(tasks);
	    assertEquals(1, tasks.size());
	    assertEquals("Task", tasks.get(0).getTitle());
	    verify(taskRepo).findAllByUserByTitle(anyString(), anyLong());
	}

	
	

	@Test
	void testUpdateTask() {
		TaskDto updatedTaskDto = new TaskDto();
        updatedTaskDto.setTitle("Updated Task");
        updatedTaskDto.setDescription("Updated Description");
        updatedTaskDto.setPriority("Low");
        updatedTaskDto.setStatus("Completed");

        when(taskRepo.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepo.save(any(Task.class))).thenReturn(task);

     
        taskService.updateTask(updatedTaskDto, 1L, user);

  
        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        assertEquals("Low", task.getPriority());
        assertEquals("Completed", task.getStatus());

        verify(emailService, times(1)).sendSimpleMail(any(Email.class), anyString());
	}

	@Test
	void testDeleteTask() {
		when(taskRepo.findById(anyLong())).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepo).delete(any(Task.class));
	}

}
