package com.incture.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
import com.incture.project.Repo.TaskRepo;
import com.incture.project.Repo.UserRepo;
import com.incture.project.Service.TaskService;
import com.incture.project.Service.UserService;
import com.incture.project.dto.TaskDto;
import com.incture.project.exception.UserNotAuthorisedException;
import com.incture.project.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class TaskController {
	@Autowired
	private TaskService taskService;
	@Autowired 
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TaskRepo taskRepo;
	
	@PostMapping("/tasks/create")
	@PreAuthorize("hasRole('ADMIN')")
	public String createTask(@RequestBody TaskDto taskDto) {
		return this.taskService.createTask(taskDto);
		
	}
	@GetMapping("/tasks/{userId}")
    @PreAuthorize("isAuthenticated()")
	public List<Task> getTasksByUserId(@PathVariable Long userId){
		return this.taskService.getTaskByUserId(userId);
		
	}
	@GetMapping("/tasks/user/{userId}")
    @PreAuthorize("isAuthenticated()")
	public List<Task> getTaskByUserByCriterion(@PathVariable Long userId,@RequestBody TaskDto taskDto) throws Exception{
		Optional<User> optionalUser  = userRepo.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User with ID " + userId + " not found.");
		}
		
		User user = optionalUser.get();
		String role = user.getRole();
		if (role.equals("ADMIN")){
			if (taskDto.getTitle() != null) {
				return this.taskService.getTasksByTitle(taskDto.getTitle());
			}
			else if (taskDto.getDescription()!=null) {
				return this.taskService.getTasksByDescription(taskDto.getDescription());
			}
			else if(taskDto.getStatus() !=null) {
				return this.taskService.getTasksByStatus(taskDto.getStatus());
			}
			else {
				return this.taskService.getTasksByPriority(taskDto.getPriority());
			}	
		}
		else {
			if (taskDto.getTitle() != null) {
				return this.taskService.getTasksByUserByTitle(taskDto.getTitle(),user);
			}
			else if (taskDto.getDescription()!=null) {
				return this.taskService.getTasksByUserByDescription(taskDto.getDescription(),user);
			}
			else if(taskDto.getStatus() !=null) {
				return this.taskService.getTasksByUserByStatus(taskDto.getStatus(),user);
			}
			else {
				return this.taskService.getTasksByUserByPriority(taskDto.getPriority(),user);
			}	
			
		}
		
		
	}
	
	
	@PutMapping("/tasks/task/{id}/user/{userId}")
	public void updateTask(@RequestBody TaskDto taskDto,
			@PathVariable("id") Long taskId,
			@PathVariable("userId") Long userId) throws Exception {
		Optional<User> optionalUser  = userRepo.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User with ID " + userId + " not found.");
		}
		User user = optionalUser.get();
		if(!user.getRole().equals("ADMIN")) {
			throw new UserNotAuthorisedException("User with ID " + userId + " not Authorized.");
		}
		this.taskService.updateTask(taskDto,taskId,user);		
	}
	
	@DeleteMapping("/tasks/task/{id}/{userId}")
	public void deleteTask (@PathVariable("id") Long taskId,
	@PathVariable("userId") Long userId) throws Exception  {
		Optional<User> optionalUser  = userRepo.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException("User with ID " + userId + " not found.");
		}
		User user = optionalUser.get();
		if(!user.getRole().equals("ADMIN")) {
			throw new UserNotAuthorisedException("User with ID " + userId + " not Authorized.");
		}
		this.taskService.deleteTask(taskId);	
		
	}
}

