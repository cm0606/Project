package com.incture.project.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.incture.project.dto.Email;
@ExtendWith(MockitoExtension.class)
class EmailServiceImpTest {
	@Mock
	private JavaMailSender javaMailSender;

	@InjectMocks
	private EmailServiceImp emailService;

	private Email email;
	
	@BeforeEach
    void setUp() {
        email = new Email();
        email.setRecipient("chetanmangal@gg.com");
        email.setMsgBody("Hi Chetan.");
        email.setSubject("Testing");
    }

	@Test
	void testSendSimpleMailSuccess() {
		String result = emailService.sendSimpleMail(email, "chetanmangal.btcivil@gg.com");
		assertNull(result);
		SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("chetanmangal.btcivil@gg.com");
        expectedMessage.setTo("chetanmangal@gg.com");
        expectedMessage.setText("Hi Chetan.");
        expectedMessage.setSubject("Testing");
        
        verify(javaMailSender).send(expectedMessage);
		
	}
	@Test
	void testSendSimpleMailFailure() {
		doThrow(new RuntimeException("Mail sending failed")).when(javaMailSender).send(any(SimpleMailMessage.class));
		String result = emailService.sendSimpleMail(email, "chetanmangal.btcivil@gg.com");
		assertEquals("chetanmangal@gg.com", result);
		verify(javaMailSender).send(any(SimpleMailMessage.class));
		
	}

}
