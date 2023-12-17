package com.yuvraj.secureApp.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.yuvraj.secureApp.entity.User;
import com.yuvraj.secureApp.entity.VerificationToken;
import com.yuvraj.secureApp.event.RegisterationCompleteEvent;
import com.yuvraj.secureApp.service.UserService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegisterationCompleteEvent>{
	@Autowired
	private UserService userService;
	@Override
	public void onApplicationEvent(RegisterationCompleteEvent event) {
		//Create verification token for the User with the link
		User user = event.getUser();
		String token = UUID.randomUUID().toString(); 
		userService.saveVerificationTokenForUser(user, token);
		//Send mail to the user
		String url = event.getApplicationURL()+"/verifyRegistration?token="+token;
		log.info("Click the link to verify :"+url);//This will be replaced by sending actual mail
	}
	
}
