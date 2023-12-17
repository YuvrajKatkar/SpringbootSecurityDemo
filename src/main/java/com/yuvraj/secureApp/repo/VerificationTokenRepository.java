package com.yuvraj.secureApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yuvraj.secureApp.entity.VerificationToken;
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{

	VerificationToken findByToken(String token);

}
