/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Note Service
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.note.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.note.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.note.dto.NoteDTO;
import com.bridgelabz.fundoonotes.note.model.Note;
import com.bridgelabz.fundoonotes.note.repository.NoteRepositoryI;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.user.repository.UserRepositoryI;
import com.bridgelabz.fundoonotes.user.response.Response;
import com.bridgelabz.fundoonotes.user.utility.Jwt;

@Service
@PropertySource("classpath:message.properties")
public class NoteService implements NoteServiceI{
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private UserRepositoryI userrepository;
	
	@Autowired
	private NoteRepositoryI noterepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Environment noteEnvironment;
	
	
	/**
	 *Method: To Create a Note for User
	 */
	@Override
	public Response createNote(String token, NoteDTO notedto) {

		String email = jwt.getEmailId(token);
		
		if(email != null) {
			Note note = mapper.map(notedto, Note.class);
			note.setEmailId(email);
			
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String date = now.format(formatter);
			note.setCreateDate(date);
			noterepository.save(note);
			
			User user = userrepository.findByEmail(email);
			user.getNotelist().add(note);
			userrepository.save(user);
			return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Create_Note"), noteEnvironment.getProperty("CREATE_NOTE"));
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: To Update a Note for User
	 */
	@Override
	public Response updateNote(String noteid, String token, NoteDTO notedto) {
		
		String email = jwt.getEmailId(token);
		
		if(email != null) {
		
			Note noteupdate = noterepository.findById(noteid).get();
			if(noteupdate != null) {
				noteupdate.setTitle(notedto.getTitle());
				noteupdate.setDescription(notedto.getDescription());
				
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String date = now.format(formatter);
				
				noteupdate.setEditDate(date);
				noterepository.save(noteupdate);
				
				Note note = noterepository.findById(noteid).get();
				User user = userrepository.findByEmail(email);
				user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
				user.getNotelist().add(noteupdate);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Update_Note"), noteEnvironment.getProperty("UPDATE_NOTE"));
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UPDATE_NOTE_NULL"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: To Delete a Note
	 */
	@Override
	public Response deleteNote(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		
		if(email != null) {
			Note note = noterepository.findById(noteid).get();
			noterepository.deleteById(noteid);
			
			user.getNotelist().remove(note);
			userrepository.save(user);
			return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Delete_Note"), noteEnvironment.getProperty("DELETE_NOTE"));
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: Search a Note using Id
	 */
	@Override
	public Response findNoteById(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		
		if(email != null) {
			Optional<Note> note =  noterepository.findById(noteid);
			return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Find_Note"), note);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
		
	}

	
	/**
	 *Method: To Display All Notes
	 */
	@Override
	public List<Note> showNotes() {
		
		return noterepository.findAll();
	}
	
	
	/**
	 *Method: To Pin/Unpin Note for User
	 */
	@Override
	public Response isPin(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		List<Note> listofNote= noterepository.findByEmailId(email);
		Note note =listofNote.stream().filter(i->i.getId().equals(noteid)).findAny().orElse(null);
		
		if(email != null)
		{
			if(noteid != null) 
			{
				note.setPin(!(note.isPin()));
				noterepository.save(note);
				
				User user = userrepository.findByEmail(email);
				Note note1 = noterepository.findById(noteid).get();
				user.getNotelist().removeIf(data -> data.getId().equals(note1.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("UPDATE_Pin"), note.isPin());
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: To Trash/Recover Note for User
	 */
	@Override
	public Response isTrash(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		List<Note> listofNote= noterepository.findByEmailId(email);
		Note note =listofNote.stream().filter(i->i.getId().equals(noteid)).findAny().orElse(null);
		
		if(email != null)
		{
			if(note.getId().equals(noteid)) {
				note.setTrash(!(note.isTrash()));
				noterepository.save(note);
				
				Note note1 = noterepository.findById(noteid).get();
				User user = userrepository.findByEmail(email);
				user.getNotelist().removeIf(data -> data.getId().equals(note1.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("UPDATE_TRASH"), note.isTrash());
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: To Archieve/Unarchieve Note for User
	 */
	@Override
	public Response isArchieve(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		List<Note> listofNote= noterepository.findByEmailId(email);
		Note note =listofNote.stream().filter(i->i.getId().equals(noteid)).findAny().orElse(null);
		User user = userrepository.findByEmail(email);
		if(email != null)
		{
			if(note.getId().equals(noteid)) {
				note.setArchieve(!(note.isArchieve()));
				noterepository.save(note);
				
				Note note1 = noterepository.findById(noteid).get();
				System.out.println("Delete Note"+note1);
				
				user.getNotelist().removeIf(data -> data.getId().equals(note1.getId()));
				System.out.println("After Deletion -> "+user.getNotelist());
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("UPDATE_ARCHIEVE"), note.isArchieve());
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}


	/**
	 *Method: To Sort Notes By Title in Ascending Order
	 */
	@Override
	public List<Note> sortNoteByTitleAsc() {
		
		List<Note> list=showNotes();
        list= list.stream().sorted((list1,list2)->list1.getTitle().compareToIgnoreCase(list2.getTitle())).collect(Collectors.toList());
        return list;
	}

	
	/**
	 *Method: To Sort Notes By Title in Descending Order
	 */
	@Override
	public List<Note> sortNoteByTitleDesc() {
		
		List<Note> list=showNotes();
        list= list.stream().sorted((list1,list2)->list2.getTitle().compareToIgnoreCase(list1.getTitle())).collect(Collectors.toList());
		return list;
	}

	
	/**
	 *Method: To Sort Notes By Date in Ascending Order
	 */
	@Override
	public List<Note> sortNoteByDateAsc() {
		
		 List<Note> list=showNotes();
         list= list.stream().sorted((list1,list2)->list1.getCreateDate().compareToIgnoreCase(list2.getCreateDate())).collect(Collectors.toList());
         return list;
	}


	/**
	 *Method: To Sort Notes By Title in Descending Order
	 */
	@Override
	public List<Note> sortNoteByDateDesc() {
		
		List<Note> list=showNotes();
        list= list.stream().sorted((list1,list2)->list2.getCreateDate().compareToIgnoreCase(list1.getCreateDate())).collect(Collectors.toList());
		return list;
	}

	
	/**
	 *Method: To Add Collaborators using Token of User
	 */
	@Override
	public Response addCollaboratorDemo(String noteid, String token) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email.equals(user.getEmail())) {
			
			Note note = noterepository.findById(noteid).get();
			if(note.getId().equals(noteid)) {
				
				boolean checker = note.getCollaboratorList().contains(email);
				if(checker) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("COLLABORATOR_EXISTS"), null);
				}
				
				if(email.equals(note.getEmailId())) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("COLLABORATOR_CANNOT_ADD"), null);
				}
				
				note.getCollaboratorList().add(email);
				noterepository.save(note);
				
				user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Add_Collaborator"), noteEnvironment.getProperty("CREATE_COLLABORATOR"));
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

	
	/**
	 *Method: To Add Collaborators using emailId
	 */
	@Override
	public Response addCollaborator(String noteid, String token, CollaboratorDTO collaboratordto) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email != null) {
			
			Note note = noterepository.findById(noteid).get();
			if(note.getId().equals(noteid)) {
				
//				List<User> listuser = userservice.showUsers();
//				List<String> users = new ArrayList<>();
//				for (int i = 0; i <listuser.size() ; i++) {
//					users.add(listuser.get(i).getEmail());
//				}
//				if(!users.contains(collaboratorEmailId)) {
//					throw new NoteException(NoteMessageReference.UNAUTHORIZED_COLLABORATOR_EXCEPTION);
//				}
				
				boolean isValid = note.getCollaboratorList().contains(collaboratordto.getCollaboratorEmail());
				if(isValid) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("COLLABORATOR_EXISTS"), null);
				}
				
				if(collaboratordto.getCollaboratorEmail().equals(note.getEmailId())) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("COLLABORATOR_CANNOT_ADD"), null);
				}
				
				note.getCollaboratorList().add(collaboratordto.getCollaboratorEmail());
				noterepository.save(note);
				
				user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Add_Collaborator"), noteEnvironment.getProperty("CREATE_COLLABORATOR"));
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}
	
	
	/**
	 *Method: To Remove Collaborators
	 */
	@Override
	public Response removeCollaborator(String noteid, String token, String collaboratorEmailId) {
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email != null) {
			
			Note note = noterepository.findById(noteid).get();
			if(noteid != null) {
				
				if(!note.getCollaboratorList().contains(collaboratorEmailId)) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("COLLABORATOR_NOT_EXISTS"), null);
				}
				
				note.getCollaboratorList().remove(collaboratorEmailId);
				noterepository.save(note);
				
				user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Remove_Collaborator"), noteEnvironment.getProperty("REMOVE_COLLABORATOR"));
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}


	/**
	 *Method: To Add Reminder
	 */
	@Override
	public Response addReminder(String token, String noteid, int year, int month, int day, int hour, int minute, int second) {
		
		String email = jwt.getEmailId(token);
		
		User user = userrepository.findByEmail(email);
		if(email != null) {
			
			Note note = noterepository.findById(noteid).get();
			if(note.getId().equals(noteid)) {
				
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(day+"-"+month+"-"+year+"   "+hour+":"+minute+":"+second);
				String date = now.format(formatter);
				
				if(note.getReminder() == null) {
					
					note.setReminder(date);
					noterepository.save(note);
					
					user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
					user.getNotelist().add(note);
					userrepository.save(user);
					return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Add_Reminder"), noteEnvironment.getProperty("ADD_REMINDER"));
				}
				
				if(note.getReminder().equals(date)) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("REMINDER_EXISTS_EXCEPTION"), null);
				}
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
		
	}

	
	/**
	 *Method: To Edit Reminder
	 */
	@Override
	public Response editReminder(String token, String noteid, int year, int month, int day, int hour, int minute, int second) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email != null) {
			
			Note note = noterepository.findById(noteid).get();
			if(note.getId().equals(noteid)) {
				
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(day+"-"+month+"-"+year+"   "+hour+":"+minute+":"+second);
				String date = now.format(formatter);
				String existingDate = note.getReminder();
				
				if(existingDate == null) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("REMINDER_NOT_FOUND_EXCEPTION"), null);
				}
				
				if(note.getReminder().equals(date)) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("REMINDER_EXISTS_EXCEPTION"), null);
				}
				
				note.setReminder(date);
				noterepository.save(note);
				
				user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Edit_Reminder"), noteEnvironment.getProperty("EDIT_REMINDER"));
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}
	
	
	/**
	 *Method: To Remove Reminder
	 */
	@Override
	public Response removeReminder(String token, String noteid) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email.equals(user.getEmail())) {
			
			Note note = noterepository.findById(noteid).get();
			if(note.getId().equals(noteid)) {
			
				if(note.getReminder() == null) {
					return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("REMINDER_REMOVE_EXCEPTION"), null);
				}
				
				note.setReminder(null);
				noterepository.save(note);
				
				user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
				user.getNotelist().add(note);
				userrepository.save(user);
				return new Response(noteEnvironment.getProperty("Status_200"), noteEnvironment.getProperty("Remove_Reminder"), noteEnvironment.getProperty("REMOVE_REMINDER"));
			}
			return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
		}
		return new Response(noteEnvironment.getProperty("Status_404"), noteEnvironment.getProperty("UNAUTHORIZED_USER_EXCEPTION"), null);
	}

}
