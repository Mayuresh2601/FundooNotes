package com.bridgelabz.fundoonotes.user.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoonotes.note.model.Note;

import lombok.Data;

@Document
@Data
public class User {

	@Id
	private String id;
	
	private String firstName;

	private String lastName;

	private String email;

	private String password;
	
	private String confirmPassword;

	private String mobileNumber;

	private boolean isValidate;

	List<Note> notelist = new ArrayList<>();
	
	private String profilePicture;
	
}
