/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @interface To connect Note Mongo Repository
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.note.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.note.model.Note;

@Repository
public interface NoteRepositoryI extends MongoRepository<Note, String>{
	
	public List<Note> findByEmailId(String email);

	public Object findByIdAndEmailId(String noteId, String emailId);

}
