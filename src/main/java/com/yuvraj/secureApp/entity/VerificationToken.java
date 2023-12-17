package com.yuvraj.secureApp.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String token;
	private Date expirationDate;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id",nullable = false,foreignKey = @ForeignKey(name = "FK_UserVerification_token"))
	private User user;
	
	private final int EXPIRATION_TIME = 10;
	public VerificationToken(String token, User user) {
		super();
		this.token = token;
		this.user = user;
		this.expirationDate = calculateExpriationDate(EXPIRATION_TIME);
	}
	public VerificationToken(String token) {
		super();
		this.token = token;
		this.expirationDate = calculateExpriationDate(EXPIRATION_TIME);
	}
	public Date calculateExpriationDate(int expirationTime) {
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTimeInMillis(new Date().getTime());
		calendar.add(Calendar.MINUTE,expirationTime);
		return new Date(calendar.getTime().getTime());
	}
	
	
}
