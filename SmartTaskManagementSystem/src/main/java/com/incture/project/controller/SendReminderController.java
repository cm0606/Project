package com.incture.project.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.project.Entity.User;
import com.incture.project.Entity.Task;
import com.incture.project.Repo.TaskRepo;
import com.incture.project.Repo.UserRepo;
import com.incture.project.Service.EmailService;
import com.incture.project.dto.Email;

@RestController
@RequestMapping("/api/v1/tasks")
public class SendReminderController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private TaskRepo taskRepo;
	 @PostMapping("/sendemail/{userId}")
	 public String sendEmail(@PathVariable Long userId) {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			String role = user.get().getRole();
			if (!role.equals("ADMIN")) {
				return "You are not authorised to send reminder";
			}
			
		}
		else {
			return "User Not Found";
		}
		User admin = user.get();
		String sender = admin.getUserEmail();
		
		LocalDate presentDate = LocalDate.now();
		List<Task> tasks = taskRepo.findAllByDate(presentDate);
		
		for (Task task : tasks) {
			Email email = new Email();
			email.setRecipient(task.getUser().getUserEmail());
			email.setSubject("Reminder for Approaching Deadline");
			email.setMsgBody("Hi " + task.getUser().getName() 
					+ "\n" + "Hope you are doing well. \n" + 
					"Just wanted to remind you that the deadline for your project " +
					task.getTitle() + " is " + task.getDeadline()+". \n" + "Best Regards"+
					"\n" + admin.getName());
			this.emailService.sendSimpleMail(email, sender);
		
		}
		return "Send";
		 
	 }
	

}
