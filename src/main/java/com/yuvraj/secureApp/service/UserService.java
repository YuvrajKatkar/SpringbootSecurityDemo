package com.yuvraj.secureApp.service;

import com.yuvraj.secureApp.entity.User;
import com.yuvraj.secureApp.entity.VerificationToken;
import com.yuvraj.secureApp.model.UserModel;

public interface UserService {

	User registerUser(UserModel userModel);
	void saveVerificationTokenForUser(User user, String token);
	boolean validateUserVerification(String token);
	VerificationToken generateNewVerificationToken(String oldToken);
}
