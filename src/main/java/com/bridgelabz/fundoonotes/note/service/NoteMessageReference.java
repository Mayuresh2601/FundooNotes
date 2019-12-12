/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class To Specify Note Messages
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.note.service;

public class NoteMessageReference {
	
	static final String CREATE_NOTE = "Note Successfully Created";
	static final String UPDATE_NOTE = "Note Successfully Updated";
	static final String DELETE_NOTE = "Note Successfully Deleted";
	public static final String NOTE_ID_NOT_FOUND = "Note ID Not Found";
	
	static final String CREATE_COLLABORATOR = "Collaborator Successfully Created";
	static final String COLLABORATOR_EXISTS = "Collaborator Already Exists";
	static final String REMOVE_COLLABORATOR = "Collaborator Successfully Removed";
	static final String COLLABORATOR_CANNOT_ADD = "Cannot Add Self Email Id Collaborator";
	static final String COLLABORATOR_NOT_EXISTS = "No Collaborator Exists";
	static final String UNAUTHORIZED_COLLABORATOR_EXCEPTION = "Collaborator is Not Authorized";
	static final String UNAUTHORIZED_USER_EXCEPTION = "User is Not Authorized";
	
	static final String ADD_REMINDER = "Reminder Added Successfully";
	static final String EDIT_REMINDER = "Reminder Edited Successfully";
	static final String REMOVE_REMINDER = "Reminder Removed Successfully";
	static final String REMINDER_REMOVE_EXCEPTION = "No Note Reminder to Remove";
	static final String REMINDER_EXISTS_EXCEPTION = "Note Reminder Already Exists";
	static final String REMINDER_NOT_FOUND_EXCEPTION = "No Note Remainder to Update";
	
}
