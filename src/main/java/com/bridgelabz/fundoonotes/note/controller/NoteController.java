/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Note Controller
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.note.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.note.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.note.dto.NoteDTO;
import com.bridgelabz.fundoonotes.note.model.Note;
import com.bridgelabz.fundoonotes.note.service.NoteService;
import com.bridgelabz.fundoonotes.user.response.Response;

@RestController
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private Environment noteEnvironment;
	
	/**Method: To create a Note
	 * @param token
	 * @param notedto
	 * @return Create Note Implementation Logic
	 */
	@PostMapping("/createnote")
	public Response createNote(@RequestHeader String token,@Valid @RequestBody NoteDTO notedto) {
		
		Response response = noteService.createNote(token, notedto);
		return response;
	}

	
	/**Method: To Update a Note
	 * @param id
	 * @param notedto
	 * @return Update Note Implementation Logic
	 */
	@PutMapping("/updatenote")
	public Response updateNote(@RequestHeader String id,@RequestHeader String token,@Valid @RequestBody NoteDTO notedto) {
		
		Response response = noteService.updateNote(id, token, notedto);
		return response;
	}

	
	/**Method: To Delete a Note
	 * @param id
	 * @return Delete Note Implementation Logic
	 */
	@DeleteMapping("/deletenote")
	public Response deleteNote(@RequestHeader String id, @RequestHeader String token) {
		
		Response response =  noteService.deleteNote(id, token);
		return response;
	}

	
	/**Method: To Find Note by Token
	 * @param id
	 * @return Find Note By Id Implementation Logic 
	 */
	@GetMapping("/notes")
	public Response findNoteById(@RequestHeader String id, @RequestHeader String token) {
		
		Response response = noteService.findNoteById(id, token);
		return response;
	}
	
	
	/**Method: To Display All Notes
	 * @return Display All Notes Implementation Logic
	 */
	@GetMapping("/shownotes")
	public Response showNotes() {
		
		List<Note> note = noteService.showNotes();
		return new Response(200, noteEnvironment.getProperty("Show_Notes"), note);
	}

	
	/**Method: To Pin/Unpin a Note
	 * @param id
	 * @param token
	 * @return Pin/Unpin a Note Implementation Logic
	 */
	@PutMapping("/notes/pin")
	public Response isPin(@RequestHeader String id, @RequestHeader String token) {
		
		Response response = noteService.isPin(id, token);
		return response;
	}

	
	/**Method: To Trash/Recover a Note
	 * @param id
	 * @param token
	 * @return Trash/Recover a Note Implementation Logic
	 */
	@PutMapping("/notes/trash")
	public Response isTrash(@RequestHeader String id, @RequestHeader String token) {
		
		Response response = noteService.isTrash(id, token);
		return response;
	}

	
	/**Method: To Archieve/Unarchieve a Note 
	 * @param id
	 * @param token
	 * @return Archieve/Unarchieve a Note Implementation Logic
	 */
	@PutMapping("/notes/archieve")
	public Response isArchieve(@RequestHeader String id, @RequestHeader String token) {
		
		Response response = noteService.isArchieve(id, token);
		return response;
	}
	
	
	/**Method: To Sort Notes By Title
	 * @return Sorted Notes By Title in Ascending Implementation Logic
	 */
	@GetMapping("/sortbytitleasc")
	public Response sortNoteByTitleAsc() {
		
		List<?> list = noteService.sortNoteByTitleAsc();
		return new Response(200, noteEnvironment.getProperty("Sort_Note_By_Title_Asc"), list);
	}
	
	
	/**Method: To Sort Notes By Title
	 * @return Sorted Notes By Title in Descending Implementation Logic
	 */
	@GetMapping("/sortbytitledesc")
	public Response sortNoteByTitleDesc() {
		
		List<?> list = noteService.sortNoteByTitleDesc();
		return new Response(200, noteEnvironment.getProperty("Sort_Note_By_Title_Desc"), list);
	}
	
	
	/**Method: To Sort Notes By Note Creation Date
	 * @return Sorted Notes By Note Creation Date in Ascending Implementation Logic
	 */
	@GetMapping("/sortbydateasc")
	public Response sortNoteByDateAsc() {
		
		List<?> list = noteService.sortNoteByDateAsc();
		return new Response(200, noteEnvironment.getProperty("Sort_Note_By_Date_Asc"), list);
	}
	
	
	/**Method: To Sort Notes By Note Creation Date
	 * @return Sorted Notes By Note Creation Date in Descending Implementation Logic
	 */
	@GetMapping("/sortbydatedesc")
	public Response sortNoteByDateDesc() {
		
		List<?> list = noteService.sortNoteByDateDesc();
		return new Response(200, noteEnvironment.getProperty("Sort_Note_By_Date_Desc"), list);
	}
	
	
	/**Method: To Add Collaborators using Users Authenticated Tokens
	 * @param id
	 * @param token
	 * @return Adding Collaborator using token Implementation Logic
	 */
	@PostMapping("/addcollaboratordemo")
	public Response addCollaboratorDemo(@RequestHeader String id,@RequestHeader String token) {
		
		Response response = noteService.addCollaboratorDemo(id, token);
		return response;
	}
	
	
	/**Method: To Add Collaborators using emailId
	 * @param id
	 * @param collaboratorEmail
	 * @return Adding Collaborator using emailId Implementation Logic
	 */
	@PostMapping("/addcollaborator")
	public Response addCollaborator(@RequestHeader String id, @RequestHeader String token, @RequestBody CollaboratorDTO collaboratordto) {
		
		Response response = noteService.addCollaborator(id, token, collaboratordto);
		return response;
	}
	
	
	/**Method: To Remove Collaborators using emailId
	 * @param id
	 * @param collaboratorEmail
	 * @return Removing Collaborator using emailId Implementation Logic
	 */
	@DeleteMapping("removecollaborator")
	public Response removeCollaborator(@RequestHeader String id, @RequestHeader String token, @RequestHeader String collaboratorEmail) {
		
		Response response = noteService.removeCollaborator(id, token, collaboratorEmail);
		return response;
	}
	
	
	/**Method: To Add Reminder
	 * @param token
	 * @param id
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return Adding Reminder Implementation Logic
	 * @throws IOException
	 */
	@PostMapping("/addreminder")
	public Response addReminder(@RequestHeader String token, @RequestHeader String id, @RequestParam int year,@RequestParam int month,@RequestParam int day,@RequestParam int hour,@RequestParam int minute,@RequestParam int second) {
		
		Response response = noteService.addReminder(token, id, year, month, day, hour, minute, second);
		return response;
	}
	
	
	/**Method: To Edit Reminder
	 * @param token
	 * @param id
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return Editing Reminder Implementation Logic
	 * @throws IOException
	 */
	@PutMapping("/editreminder")
	public Response editReminder(@RequestHeader String token, @RequestHeader String id, @RequestParam int year,@RequestParam int month,@RequestParam int day,@RequestParam int hour,@RequestParam int minute,@RequestParam int second) {
		
		Response response = noteService.editReminder(token, id, year, month, day, hour, minute, second);
		return response;
	}
	
	
	/**Method: To Remove Reminder
	 * @param token
	 * @param id
	 * @return Remove Reminder Implementation Logic
	 * @throws IOException
	 */
	@DeleteMapping("/removereminder")
	public Response removeReminder(@RequestHeader String token, @RequestHeader String id) {
		
		Response response = noteService.removeReminder(token, id);
		return response;
	}

}
