package com.incture.project.dto;

public class UserDto {
	private String name;
    private String userName;
    private String userEmail;
    private String password;
    private String role;
    
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDto(String name,String userName, String userEmail, String password, String role) {
		super();
		this.name = name;
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDto [name=" + name + ", userName=" + userName + ", userEmail=" + userEmail + ", password="
				+ password + ", role=" + role + "]";
	}
	
    

}
