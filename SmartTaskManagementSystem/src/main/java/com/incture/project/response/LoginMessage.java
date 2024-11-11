package com.incture.project.response;

public class LoginMessage {
	String message;
	Boolean status;
	
	public LoginMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoginMessage(String message, Boolean status) {
		super();
		this.message = message;
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LoginMessage [message=" + message + ", status=" + status + "]";
	}
	
	

}
