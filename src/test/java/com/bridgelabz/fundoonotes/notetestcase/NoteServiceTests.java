package com.bridgelabz.fundoonotes.notetestcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bridgelabz.fundoonotes.note.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.note.dto.NoteDTO;
import com.bridgelabz.fundoonotes.note.model.Note;
import com.bridgelabz.fundoonotes.note.repository.NoteRepositoryI;
import com.bridgelabz.fundoonotes.note.service.NoteService;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.user.repository.UserRepositoryI;
import com.bridgelabz.fundoonotes.user.response.Response;
import com.bridgelabz.fundoonotes.user.utility.Jwt;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("classpath:message.properties")
public class NoteServiceTests {
	
	@InjectMocks
	private NoteService noteService;
	
	@Mock
	private NoteRepositoryI noteRepo;
	
	@Mock
	private UserRepositoryI userRepo;
	
	@Mock
	private User user;
	
	@Mock
	private CollaboratorDTO collaboratordto;
	
	@Mock
	private Jwt jwt;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private Environment noteEnv;
	
	
	/* Parameters Used */
	private Note note = new Note();
	private NoteDTO noteDTO = new NoteDTO();
	private Optional<Note> optionalNote = Optional.of(note);
	private String noteId = "5dea5e42836c8f441d888498";
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibXNzb25hcjI2QGdtYWlsLmNvbSJ9.PnmJiMaZVOJ03T5WgZU8k0VNEK-Osgj-mCtWe2whkUQ";
	private String emailId = "mssonar26@gmail.com";
	private String collaboratorEmailId = "demo.mayuresh@gmail.com";
	private String reminder = "2019/12/31 24:00:00";
	private int year=2021,month=6,day=12,hour=18,minute=17,second=57;
	
	
	/**
	 * Method: Test Case for Create Note
	 */
	@Test
	public void testCreateNote() {

		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(mapper.map(noteDTO, Note.class)).thenReturn(note);
		when(noteRepo.save(note)).thenReturn(note);
	
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		user.getNotelist().remove(note);
		when(userRepo.save(user)).thenReturn(user);
		
		Response response = noteService.createNote(token, noteDTO);
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	 * Method: Test Case for Update Note
	 */
	@Test
	public void testUpdateNote() {
		
		NoteDTO noteDTO = new NoteDTO();
		noteDTO.setTitle("Secrets Can't Share");
		noteDTO.setDescription("My Secrets are my secrets none of your secrets");
	
		note.setId(noteId);
		note.setEmailId(emailId);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		when(mapper.map(noteDTO, Note.class)).thenReturn(note);
		when(noteRepo.save(note)).thenReturn(note);		
		
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
		user.getNotelist().remove(note);
		when(userRepo.save(user)).thenReturn(user);
		
		Response response = noteService.updateNote(noteId, token, noteDTO);
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	* Method: Test Case for Delete Note 
	*/
	@Test
	public void testDeleteNote() {
		
		note.setId(noteId);
		note.setEmailId(emailId);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		noteRepo.deleteById(noteId);
		
		user.getNotelist().remove(note);
		when(userRepo.save(user)).thenReturn(user);
		
		Response response= noteService.deleteNote(noteId, token);
		assertEquals(200, response.getStatus());
	}

	  
	/**
	 * Method: Test case to get all note
	 */
	@Test
	public void testfindNoteById() {

		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);

		Response response = noteService.findNoteById(noteId, token);
		assertEquals(200, response.getStatus());

	}
	
	
	/**
	* Method: Test Case to Show All Notes
	*/
	@Test
	public void testShowNotes() {
		
		List<Note> notelist = noteService.showNotes();
		assertEquals(notelist, noteRepo.findAll());
	}

	
	/**
	* Method: Test Case to Pin Note for User 
	*/
	@Test
	public void TestisPin() {

		note.setPin(true);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		
		if (note.isPin()) {
			note.setPin(true);
		}
		else {
			note.setPin(false);
		}
		assertEquals(true, note.isPin());
	
	}

	
	/**
	* Method: Test Case to Trash Note for User 
	*/
	@Test
	public void testIsTrash() {
		
		note.setTrash(true);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
	
		if (note.isTrash()) {
			note.setTrash(true);
		}
		else {
			note.setTrash(false);
		}
		assertEquals(true, note.isTrash());
	}

	
	/**
	* Method: Test Case to Archieve Note for User 
	*/
	@Test
	public void testIsArchieve() {
		
		note.setArchieve(true);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		
		if (note.isArchieve()) {
			note.setArchieve(true);
		}
		else {
			note.setArchieve(false);
		}
		assertEquals(true, note.isArchieve());
	}
	
	
	/**
	 * Method: Test Case to sort Note By Title in Ascending Order
	 */
	@Test
	public void testSortNoteByTitleAsc() {
		
		List<?> notelist = noteService.sortNoteByTitleAsc();
		assertEquals(notelist, noteRepo.findAll());
	}
	
	
	/**
	 * Method: Test Case to sort Note By Title in Descending Order
	 */
	@Test
	public void testSortNoteByTitleDesc() {
		
		List<?> notelist = noteService.sortNoteByTitleDesc();
		assertEquals(notelist, noteRepo.findAll());
	}
	
	
	/**
	 * Method: Test Case to sort Note By Date in Ascending Order
	 */
	@Test
	public void testSortNoteByDateAsc() {
		
		List<?> notelist = noteService.sortNoteByDateAsc();
		assertEquals(notelist, noteRepo.findAll());
	}
	
	
	/**
	 * Method: Test Case to sort Note By Date in Descending Order
	 */
	@Test
	public void testSortNoteByDateDesc() {
		
		List<?> notelist = noteService.sortNoteByDateAsc();
		assertEquals(notelist, noteRepo.findAll());
	}
	
	
	/**
	 * Method: Test Case to Add Collaborator
	 */
	public void testAddCollaborator() {

		collaboratordto.setCollaboratorEmail(collaboratorEmailId);
		note.setId(noteId);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		
		assertTrue(note.getId().equals(noteId));

		assertThat(note.getCollaboratorList().contains(collaboratordto.getCollaboratorEmail()));
		if(collaboratordto.getCollaboratorEmail().equals(note.getEmailId())) {
		
		note.getCollaboratorList().add(emailId);
		when(noteRepo.save(note)).thenReturn(note);
		
		assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
		user.getNotelist().add(note);
		when(userRepo.save(user)).thenReturn(user);
		
		Response response = noteService.addCollaborator(noteId, token, collaboratordto);
		assertEquals(200, response.getStatus());
		}
	}

	
	/**
	 * Method: Test Case to Remove Collaborator
	 */
	@Test
	public void testRemoveCollaborator() {

		collaboratordto.setCollaboratorEmail(emailId);
		note.getCollaboratorList().add(emailId);

		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		
		if(note.getCollaboratorList().contains(collaboratordto.getCollaboratorEmail())) {

			note.getCollaboratorList().remove(emailId);
			when(noteRepo.save(note)).thenReturn(note);

			assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
			user.getNotelist().add(note);
			when(userRepo.save(user)).thenReturn(user);
			
			Response response = noteService.removeCollaborator(noteId, token, collaboratorEmailId);
			assertEquals(200, response.getStatus());
		}
	}
	
	
	/**
	 * Method: Test Case to Add Reminder
	 */
	@Test
	public void testAddReminder() {
		
		note.setId(noteId);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		assertTrue(note.getId().equals(noteId));

		if (note.getReminder() != null) {
			note.setReminder(reminder);
			when(noteRepo.save(note));

			assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
			user.getNotelist().add(note);
			when(userRepo.save(user)).thenReturn(user);

			Response response = noteService.editReminder(token, noteId, year, month, day, hour, minute, second);
			assertEquals(200, response.getStatus());
		}
	}
	
	
	/**
	 * Method: Test Case to Edit Reminder
	 */
	@Test
	public void testEditReminder() {
		
		note.setId(noteId);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		assertTrue(note.getId().equals(noteId));

		if (note.getReminder() != null) {
			note.setReminder(reminder);
			when(noteRepo.save(note));

			assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
			user.getNotelist().add(note);
			when(userRepo.save(user)).thenReturn(user);

			Response response = noteService.editReminder(token, noteId, year, month, day, hour, minute, second);
			assertEquals(200, response.getStatus());
		}
	}
	
	
	/**
	 * Method: Test Case to Remove Reminder 
	 */
	@Test
	public void testRemoveReminder() {
		
		note.setId(noteId);
		
		// Mock Object Defined
		when(jwt.getEmailId(token)).thenReturn(emailId);
		when(userRepo.findByEmail(emailId)).thenReturn(user);
		when(noteRepo.findById(noteId)).thenReturn(optionalNote);
		assertTrue(note.getId().equals(noteId));
		
		if (note.getReminder() != null) {
			note.setReminder(null);
			when(noteRepo.save(note));

			assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
			user.getNotelist().add(note);
			when(userRepo.save(user)).thenReturn(user);

			Response response = noteService.removeCollaborator(noteId, token, collaboratorEmailId);
			assertEquals(200, response.getStatus());
		}
	}
}
