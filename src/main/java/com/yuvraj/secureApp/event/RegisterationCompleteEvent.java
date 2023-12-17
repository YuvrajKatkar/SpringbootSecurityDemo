package com.yuvraj.secureApp.event;

import org.springframework.context.ApplicationEvent;

import com.yuvraj.secureApp.entity.User;

import lombok.Data;
@Data
public class RegisterationCompleteEvent extends ApplicationEvent{
	private User user;
	private String applicationURL;
	public RegisterationCompleteEvent(User user,String applicationURL) {
		super(user);
		this.user = user;
		this.applicationURL = applicationURL;
	}

}
