package com.incture.project.Service;

import com.incture.project.dto.Email;

public interface EmailService {
	String sendSimpleMail(Email email, String sender);

	
}
