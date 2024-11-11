package com.incture.project.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
import com.incture.project.Repo.TaskRepo;
import com.incture.project.Repo.UserRepo;
import com.incture.project.dto.Email;
import com.incture.project.dto.TaskDto;
import com.incture.project.exception.TaskNotFoundException;
import com.incture.project.exception.UserNotFoundException;
@Service
public class TaskServiceImpl implements TaskService{
	private static  final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TaskRepo taskRepo;
	@Autowired
	private EmailService emailService;
	@Override
	public String createTask(TaskDto taskDto) {
		try {
			LOGGER.info("Attempting to create task with title: " + taskDto.getTitle());
			User user = userRepo.findById(taskDto.getUserId()).orElseThrow(()->{
				LOGGER.error("User with id: "+ taskDto.getUserId() + " not found");
				return new UserNotFoundException("User with id: "+ taskDto.getUserId() + " not found");
			});
			Task task = new Task();
	        task.setTitle(taskDto.getTitle());
	        task.setDescription(taskDto.getDescription());
	        task.setPriority(taskDto.getPriority());
	        task.setUser(user);
	        task.setDeadline(taskDto.getDeadline());
	        task.setReminder(taskDto.getReminder());
	        task.setStatus(taskDto.getStatus());
			taskRepo.save(task);
			LOGGER.info("Task created successfully with title: " + taskDto.getTitle());
			return "Task Created Successfully";
		}
		catch(Exception e) {
            LOGGER.error("Error occurred while creating task", e.getMessage());
            throw new RuntimeException("Error occurred while creating task");
        }
	}
	@Override
	public List<Task> getTaskByUserId(Long userId) {
		LOGGER.info("Fetching tasks for user with id: " +  userId);
		
		try {
			List<Task> tasks = taskRepo.findByUserId(userId);
			LOGGER.info("Fetched {} tasks for user with id: " +  userId , tasks.size());
			return tasks;
		}
		catch (Exception e) {
			LOGGER.error("Error occurred while fetching tasks for user with id: " + userId, e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
	}
		
	@Override
	public List<Task> getTasksByTitle(String title){
		LOGGER.info("Fetching tasks with title: " +  title);
		try {
			List<Task> tasks = taskRepo.findAllByTitle(title);
			LOGGER.info("Fetched {} tasks with title: " +  title , tasks.size());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks with title: " +  title, e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
	}
	@Override
	public List<Task> getTasksByDescription(String description) {
		LOGGER.info("Fetching tasks with description: " +  description);
		try {
			List<Task> tasks = taskRepo.findAllByDescription(description);
			LOGGER.info("Fetched {} tasks with description: " +  description , tasks.size());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks with description: " +  description, e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
		
	}
	@Override
	public List<Task> getTasksByStatus(String status) {
		LOGGER.info("Fetching tasks with status: " +  status);
		try {
			List<Task> tasks = taskRepo.findAllByStatus(status);
			LOGGER.info("Fetched {} tasks with status: " +  status , tasks.size());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks with status: " +  status, e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
		
	}
	@Override
	public List<Task> getTasksByPriority(String priority) {
		LOGGER.info("Fetching tasks with priority: " +  priority);
		try {
			List<Task> tasks = taskRepo.findAllByPriority(priority);
			LOGGER.info("Fetched {} tasks with priority: " +  priority , tasks.size());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks with priority: " +  priority, e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
	}
	@Override
	public List<Task> getTasksByUserByTitle(String title, User user) {
		LOGGER.info("Fetching tasks for user with id: {} and title: " +  title, user.getId());
		try {
			List<Task> tasks = taskRepo.findAllByUserByTitle(title,user.getId());
			LOGGER.info("Fetched {} tasks for user with id: {} and title: " +  title , tasks.size(),user.getId());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks for user with id: {} and title: " +  title,user.getId() ,e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
		
	}
	@Override
	public List<Task> getTasksByUserByDescription(String description, User user) {
		LOGGER.info("Fetching tasks for user with id: {} and description: " +  description, user.getId());
		try {
			List<Task> tasks = taskRepo.findAllByUserByDescription(description,user.getId());
			LOGGER.info("Fetched {} tasks for user with id: {} and description: " +  description , tasks.size(),user.getId());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks for user with id: {} and description: " +  description,user.getId() ,e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
		
	}
	@Override
	public List<Task> getTasksByUserByStatus(String status, User user) {
		LOGGER.info("Fetching tasks for user with id: {} and status: " +  status, user.getId());
		try {
			List<Task> tasks = taskRepo.findAllByUserByStatus(status,user.getId());
			LOGGER.info("Fetched {} tasks for user with id: {} and status: " +  status , tasks.size(),user.getId());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks for user with id: {} and status: " +  status, user.getId() ,e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
		
	}
	@Override
	public List<Task> getTasksByUserByPriority(String priority, User user) {
		LOGGER.info("Fetching tasks for user with id: {} and priority: " +  priority, user.getId());
		try {
			List<Task> tasks = taskRepo.findAllByUserByPriority(priority,user.getId());
			LOGGER.info("Fetched {} tasks for user with id: {} and priority: " +  priority , tasks.size(),user.getId());
			return tasks;
		}
		catch(Exception e) {
			LOGGER.error("Error occurred while fetching tasks for user with id: {} and priority: " +  priority, user.getId() ,e.getMessage());
            throw new RuntimeException("Error occurred while fetching tasks");
        }
	}
	@Override
	public void updateTask(TaskDto taskDto, Long taskId, User admin) {
		LOGGER.info("Attempting to update task with id: "+ taskId);
		try {
			Task task = taskRepo.findById(taskId)
				.orElseThrow(()->{
					LOGGER.error("Task with id: {} not found",taskId);
					return new TaskNotFoundException("Task with id: "+ taskId + " not found");
				});
			if (taskDto.getUserId() != null && task.getUser().getId() != taskDto.getUserId()) {
				Email email = new Email();
				String recieverEmail = task.getUser().getUserEmail();
				email.setRecipient(recieverEmail);
				email.setMsgBody("Hi " + task.getUser().getName() 
						+ "\n" + "Hope you are doing well. \n" + 
						"Just wanted to inform you that task " +
						task.getTitle() + " has been given to some another user. You will be alloted new task shortly "+". \n" + "Best Regards"+
						"\n" + admin.getName());
				email.setSubject("Task Unassigned");
				this.emailService.sendSimpleMail(email, admin.getUserEmail());
				task.setUser(this.userRepo.getById(taskDto.getUserId()));
			}
			if (taskDto.getTitle() != null) {
				task.setTitle(taskDto.getTitle());
			}
			if (taskDto.getDescription() != null) {
				task.setDescription(taskDto.getDescription());
			}
			if (taskDto.getDeadline() != null) {
				task.setDeadline(taskDto.getDeadline());
			}
			if (taskDto.getReminder() != null) {
				task.setReminder(taskDto.getReminder());
			}
			if (taskDto.getPriority() != null) {
				task.setPriority(taskDto.getPriority());
			}
			if (taskDto.getStatus() != null) {
				task.setStatus(taskDto.getStatus());
			}
			Email email = new Email();
			String recieverEmail = task.getUser().getUserEmail();
			email.setRecipient(recieverEmail);
			email.setMsgBody("Hi " + task.getUser().getName() 
					+ "\n" + "Hope you are doing well. \n" + 
					"Just wanted to inform you that there are some changes in the task " +
					task.getTitle() + " .So see the details and do project accordingly"+". \n" + "Best Regards"+
					"\n" + admin.getName());
			email.setSubject("Updates in Task");
			this.emailService.sendSimpleMail(email, admin.getUserEmail());
			this.taskRepo.save(task);
			LOGGER.info("Task with id: {} has been successfully updated", taskId);
		}
		catch (Exception e) {
            LOGGER.error("Error occurred while updating task with id: " + taskId, e.getMessage());
            throw new RuntimeException("Error occurred while updating task");
        }
		
		
	}
	@Override
	public void deleteTask(Long taskId) {
		LOGGER.info("Attempting to delete task with id: " + taskId);
		try {
			Task task = taskRepo.findById(taskId).orElseThrow(()->{
				LOGGER.error("Task with id: {} not found",taskId);
				return new TaskNotFoundException("Task with id: "+ taskId + " not found");
			});
			this.taskRepo.delete(task);
			LOGGER.info("Task with id: {} has been successfully deleted", taskId);
		}catch (Exception e) {
			LOGGER.error("Error occurred while deleting task with id: " + taskId, e.getMessage());
            throw new RuntimeException("Error occurred while deleting task");
		}
		
	}

}
