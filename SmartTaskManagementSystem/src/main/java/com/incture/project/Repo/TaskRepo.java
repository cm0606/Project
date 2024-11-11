package com.incture.project.Repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incture.project.Entity.Task;
import com.incture.project.Entity.User;
@Repository
public interface TaskRepo extends JpaRepository<Task,Long>{
	@Query("SELECT t FROM Task t WHERE t.user.id = ?1")
	List<Task> findByUserId(Long userId);
	@Query("SELECT t FROM Task t WHERE t.title = ?1")
	List<Task> findAllByTitle(String title);
	@Query("SELECT t FROM Task t WHERE t.description = ?1")
	List<Task> findAllByDescription(String description);
	@Query("SELECT t FROM Task t WHERE t.status = ?1")
	List<Task> findAllByStatus(String status);
	@Query("SELECT t FROM Task t WHERE t.priority = ?1")
	List<Task> findAllByPriority(String priority);
	@Query("SELECT t FROM Task t WHERE t.reminder = ?1")
	List<Task> findAllByDate(LocalDate presentDate);
	@Query("SELECT t FROM Task t WHERE t.title = ?1 and t.user.id = ?2")
	List<Task> findAllByUserByTitle(String title, Long userId);
	@Query("SELECT t FROM Task t WHERE t.description = ?1 and t.user.id = ?2")
	List<Task> findAllByUserByDescription(String description, Long userId);
	@Query("SELECT t FROM Task t WHERE t.status = ?1 and t.user.id = ?2")
	List<Task> findAllByUserByStatus(String status, Long userId);
	@Query("SELECT t FROM Task t WHERE t.priority = ?1 and t.user.id = ?2")
	List<Task> findAllByUserByPriority(String priority, Long userId);

}
