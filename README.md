Smart Task Management System

Description: The Smart Task Management System is a web application designed to help users manage their task efficiently. Users can create, update, delete, and track their tasks, set deadlines, prioritize tasks, and categorize them.

Features: 
 User Authentication: User registration and login functionality. Role management (e.g., Admin, User). 
 Task Management: CRUD tasks. Assign tasks to user. Set deadlines and reminders. Prioritize tasks (High, Medium, Low). 
 Search and Filter: Search tasks by title or description. Filter tasks by status (Completed, Pending) and priority.
 Notifications: Email notification for upcoming deadlines.
 Logging and Error Handling: Implement logging and system errors.  Testing: Unit and integration tests using JUnit.

Overview

This report describes the design and implementation of a simple REST API for an Smart Task Management System that allows to perform CRUD operations on Users (Registration and Login) and Tasks. The application uses Spring Boot for building the REST service. Additionally, unit tests are included for verifying the API's functionality.

Key Features: 

User-Related Features:
User Registration: 
  o Users can create a new account by providing details like name, username, email, password, and role. 
  o Role-based access control for different user roles (e.g., Admin, User).
User Authentication: 
  o Users can log in using their username and password. 
  o Authentication ensures that only authorized users can access the system.
Password Management: 
  o Passwords are securely stored and encrypted.
  o Password matching is verified during login.
Email Management: 
  o Users can update their email address. 
  o Automatic email notifications are sent when certain tasks or actions are updated (e.g., task reassignment, task updates).
User Deletion: 
  o Users can be deleted from the system if required.
  o Associated data (tasks) may also be impacted when a user is deleted.
Role and Permissions: 
  o Role-based permissions control the level of access users have to tasks and other resources.
  o Admin users have elevated privileges (e.g., creating tasks, assigning users to tasks, modifying tasks).
  
Task-Related Features:
Task Creation: 
  o Tasks can be created by users, including key attributes such as title, description, priority, status, deadline, and reminders. 
  o Tasks are linked to specific users (e.g., team members) for assignment.

Task Retrieval: 
  o Tasks can be retrieved based on different search criteria such as: 
     By User: Retrieve tasks assigned to a specific user. 
     By Title: Retrieve tasks based on the task title. 
     By Description: Retrieve tasks based on the task description. 
     By Status: Retrieve tasks with specific statuses (e.g., Pending, In Progress, Completed). 
     By Priority: Retrieve tasks filtered by priority (e.g., High, Medium, Low).

Task Update: 
  o Admins can update task details such as title, description, status, priority, and deadline.
  o If a task is reassigned to a different user, an email notification is sent to inform the previous user. 
  o Task status updates trigger Email notifications to the relevant users.

Task Deletion: 
  o Users can delete tasks, removing them from the system. 
  o The system ensures that tasks are properly deleted, and relevant audit logs are maintained for such actions.

Task Assignment: 
  o Tasks can be assigned or reassigned to users. 
  o When a task is reassigned, the previously assigned user is notified via email.

Task Prioritization:
  o Tasks can be assigned a priority (e.g., High, Medium, Low) to help users focus on critical tasks first.

Task Deadline and Reminder: 
  o Admins can set due dates for tasks. 
  o Notifications are sent when current date matches the reminder date stored in task by the admin.

Email Notifications for Task Updates:
  o Emails are sent to users when there is update to any task. 
  
Tools and Technologies Used 
  • Java 
  • Springboot Framework 
  • Maven dependencies 
  • SMTP Server: for send gmails. 
  • MY- SQL Database 
  • Git

Solution Design

API Endpoints 
The API has the following endpoints: 
User Management:
Register a New User 
  • POST (/api/v1/user/signUp)
  • RequestBody {"name" : "Chetan Mangal", "userName" : "chetan06", "userEmail" : chetanmangal6g.com, "password" : "Cm02%2", "role" : "ADMIN"} 
  • Response: "User Created Successfully"
Login a User
  • POST (/api/v1/user/login)
  • Request Body { "userName" : "chetan06", "password" : "Cm02%2"} 
  • Response {"message": "Login Success", "success": true}
Update User Email 
  • PUT (/api/v1/user/update/{userId}) 
  • Request Body {"userEmail" : chetangal6g.com} 
  • Response “User email updated successfully for userId: ”+ userId
Delete a User 
  • DELETE (/api/v1/user/{userId})

  
Task Management:

Create a New Task 
  • POST (“/api/v1/tasks/create”) 
  • Request Body {"title": "Task", "description":" manage tasks", "deadline": "2024-11-11", "reminder": "2024-11-09", "priority": "High", "status": "Pending", "userId": 2}   • Response “Task created Successfully”

Get List Tasks By User Id 
  • GET (“/api/v1/tasks/{userId}”) 
  • Response (List of tasks appointed to that user)

Get List Tasks By Criterian 
  • GET(/api/v1/tasks/user/{userId}) 
  • RequestBody (description or title or status or priority etc)
  • Response ( list of all tasks that meet the criteria if userId is of admin else list of all tasks, of a particular user, that meet criteria)

Update Task 
  • PUT (/api/v1/tasks/task/{id}/user/{userId} 
  • RequestBody (every element of task that is needed to be updated

Delete task 
  • DELETE (/api/v1/tasks/task/{id}/{userId}

Email Management:

Send Reminder Emails 
  • POST (/api/v1/tasks/sendemail/{userId})
  • Response (“Send”) 
  • Emails will be send By only one email that is being authorized by google but only the admins will be able to send the email. They will be send if reminder date meets   present date.
  
Data Model

User 
  • Id: (Unique and Auto Generated) 
  • Name 
  • UserName (Unique) 
  • UserEmail (Unique) 
  • Password (BCrypted) 
  • Role (ADMIN or USER) 
  • Set task (@OneToMany(mappedBy = "user", cascade = CascadeType.ALL))

Task 
  • Id: (Unique and Auto Generated) 
  • Title • Description • Deadline 
  • Reminder 
  • Priority (High, Medium, Low) 
  • Status (Completed, Pending) 
  • User (@ManyToOne @JoinColumn(name = "user_id"))

Email 
  • Recipient 
  • MsgBody 
  • Subject

Security Implementation 
  • Basic or token-based authentication secures user data.

Logging and Error Handling 
  • Any change In User and Task Table has been Logged • Every method in Service class is implemented in try and catch blocks to handle errors.

Testing Unit tests are implemented using JUnit for thorough coverage: 
 • SendReminderControllerTest 
 • TaskControllerTest 
 • UserControllerTest 
 • EmailServiceImpTest 
 • TaskServiceImpTest 
 • UserServiceImpTest

Project Files

  Github Link: Project/SmartTaskManagementSystem at main · cm0606/Project

Future Scope

  • Front End part could be implemented. 
  • Authentication and Authorization implemented by me is using Springboot Security Basic Authentication. We could use JWT Authentication in Future. 
  • In Application Notification could be implemented.

Conclusion

This comprehensive report outlines the design, implementation, and testing of the SmartTaskManagement application, providing an in-depth analysis of its architecture, security features, and core functionalities. The project aims to streamline task management by offering users a flexible, intuitive platform to efficiently track, prioritize, and collaborate on tasks.
