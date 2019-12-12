/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @interface To connect User Mongo Repository
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.user.model.User;

@Repository
public interface UserRepositoryI extends MongoRepository<User, String>{
	
	public User findByEmail(String email);

}