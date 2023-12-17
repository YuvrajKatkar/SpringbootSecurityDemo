package com.yuvraj.secureApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuvraj.secureApp.entity.User;
import com.yuvraj.secureApp.entity.VerificationToken;
import com.yuvraj.secureApp.event.RegisterationCompleteEvent;
import com.yuvraj.secureApp.model.UserModel;
import com.yuvraj.secureApp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RegisterationController {
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationEventPublisher publisher;
	@GetMapping("/hello")
	public String heello() {
		return "Hi, Yuvraj";
	}
	@GetMapping("/resendVerificationToken")
	public String resendverificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
		VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
		User user = verificationToken.getUser();
		resendverificationTokenMail(user,applicationURL(request),verificationToken.getToken());
		return "Verification link send";
	}
	private void resendverificationTokenMail(User user, String applicationURL,String token) {
		String url = applicationURL+"/verifyRegistration?token="+token;
		log.info("Click the link to verify :"+url);
		
	}
	@GetMapping("/verifyRegistration")
	public String verifyUser(@RequestParam("token") String token) {
		boolean result = userService.validateUserVerification(token);
		if(result) return "Validation confirmed";
		else return "Bad creds";
	}
	@PostMapping("/register")
	public String userRegistration(@RequestBody UserModel userModel,final HttpServletRequest request) {
		User user = userService.registerUser(userModel);
		System.out.println(user);
		publisher.publishEvent(new RegisterationCompleteEvent(user,applicationURL(request)));
		return "Success";
	}
	public String applicationURL(HttpServletRequest request) {
		return "https://"+request.getServerName()+":"+
				request.getServerPort()+request.getContextPath();
	}
}
