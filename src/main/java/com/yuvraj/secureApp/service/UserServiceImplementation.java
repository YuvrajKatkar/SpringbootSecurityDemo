package com.yuvraj.secureApp.service;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuvraj.secureApp.entity.User;
import com.yuvraj.secureApp.entity.VerificationToken;
import com.yuvraj.secureApp.model.UserModel;
import com.yuvraj.secureApp.repo.UserRepository;
import com.yuvraj.secureApp.repo.VerificationTokenRepository;

@Service
public class UserServiceImplementation implements UserService{
	@Autowired 
	private UserRepository userRepository;
	@Autowired 
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	@Override
	public User registerUser(UserModel userModel) {
		User user = new User();
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setEmail(userModel.getEmail());
		user.setRole("USER");
		user.setPassword(encoder.encode(userModel.getPassword()));
		return userRepository.save(user);
	}
	public void saveVerificationTokenForUser(User user, String token) {
		VerificationToken verificationToken  = new VerificationToken(token,user);
		verificationTokenRepository.save(verificationToken);
	}
	@Override
	public boolean validateUserVerification(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
		if(verificationToken==null) return false;
		User user = verificationToken.getUser();
		Calendar calendar = Calendar.getInstance();
		if((verificationToken.getExpirationDate().getTime()
				-calendar.getTime().getTime())<0) {
			verificationTokenRepository.delete(verificationToken);
			return false;
		}
		user.setEnabled(true);
		userRepository.save(user);
		return true;
	}
	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(oldToken);
		verificationToken.setToken(UUID.randomUUID().toString());
		return	verificationTokenRepository.save(verificationToken);
	}
}
