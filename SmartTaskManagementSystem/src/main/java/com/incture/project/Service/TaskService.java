package com.incture.project.Service;

import java.util.List;

import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
import com.incture.project.dto.TaskDto;

public interface TaskService {

	String createTask(TaskDto taskDto);

	List<Task> getTaskByUserId(Long userId);

	List<Task> getTasksByTitle(String title);

	List<Task> getTasksByDescription(String description);

	List<Task> getTasksByStatus(String status);

	List<Task> getTasksByPriority(String priority);

	List<Task> getTasksByUserByTitle(String title, User user);

	List<Task> getTasksByUserByDescription(String description, User user);

	List<Task> getTasksByUserByStatus(String status, User user);

	List<Task> getTasksByUserByPriority(String priority, User user);

	void updateTask(TaskDto taskDto, Long taskId, User user);

	void deleteTask(Long taskId);

}
