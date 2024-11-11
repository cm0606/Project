package com.incture.project.dto;

import java.time.LocalDate;


public class TaskDto {
	private String title;
    private String description;
    private LocalDate deadline;
    private LocalDate reminder;
	private String priority; // High, Medium, Low
    private String status; // Completed, Pending
    private Long userId;
    public TaskDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskDto(String title, String description, LocalDate deadline, LocalDate reminder, String priority,
			String status, Long userId) {
		super();
		this.title = title;
		this.description = description;
		this.deadline = deadline;
		this.reminder = reminder;
		this.priority = priority;
		this.status = status;
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}
	public LocalDate getReminder() {
		return reminder;
	}
	public void setReminder(LocalDate reminder) {
		this.reminder = reminder;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
    

}
