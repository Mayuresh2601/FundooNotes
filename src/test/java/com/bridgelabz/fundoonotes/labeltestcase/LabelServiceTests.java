package com.bridgelabz.fundoonotes.labeltestcase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.bridgelabz.fundoonotes.label.dto.LabelDTO;
import com.bridgelabz.fundoonotes.label.model.Label;
import com.bridgelabz.fundoonotes.label.repository.LabelRepositoryI;
import com.bridgelabz.fundoonotes.label.service.LabelService;
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
public class LabelServiceTests {
	
	@InjectMocks
	private LabelService labelService;
	
	@Mock
	private NoteService noteService;
	
	@Mock
	private UserRepositoryI userRepo;
	
	@Mock
	private NoteRepositoryI noteRepo;
	
	@Mock
	private LabelRepositoryI labelRepo;
	
	@Mock
	private LabelDTO labeldto;
	
	@Mock
	private Jwt jwt;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private Environment labelEnv;
	
	/* Used Objects */
	private Label label = new Label();
	private Note note = new Note();
	private User user = new User();
	private String noteid = "5dea5dee836c8f441d888494";
	private String labelid = "5dea5dee836c8f441d888494";
	private String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbElkIjoibXNzb25hcjI2QGdtYWlsLmNvbSJ9.PnmJiMaZVOJ03T5WgZU8k0VNEK-Osgj-mCtWe2whkUQ";
	private String email = "mssonar26@gmail.com";
	private Optional<Label> optionalLabel = Optional.of(label);
	private Optional<Note> optionalNote = Optional.of(note);
	private List<Note> notelist = new ArrayList<>();


	/**
	 * Method: Test Case to Create Label
	 */
	@Test
	public void testCreateLabel() {
		
		when(jwt.getEmailId(token)).thenReturn(email);
		when(userRepo.findByEmail(email)).thenReturn(user);
		when(mapper.map(labeldto, Label.class)).thenReturn(label);
		user.setEmail(email);
		
		Response response = labelService.createLabel(token, labeldto);
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	 * Method: Test Case to Update Label
	 */
	@Test
	public void testUpdateLabel() {
		
		label.setId(labelid);
		note.setId(noteid);
		note.setEmailId(email);
		when(jwt.getEmailId(token)).thenReturn(email);
		when(labelRepo.findById(labelid)).thenReturn(optionalLabel);
		when(noteRepo.findById(noteid)).thenReturn(optionalNote);
	
		when(mapper.map(labeldto, Label.class)).thenReturn(label);

		assertTrue(note.getId().equals(noteid));
		assertTrue(label.getId().equals(labelid));
		when(labelRepo.save(label)).thenReturn(label);
		
		//when(noteService.showNotes()).thenReturn(notelist);
		//notelist.stream().filter(data -> data.getEmailId().equals(email)).findAny().orElse(null);
//		assertThat(note.getLabellist().removeIf(data -> data.getId().equals(labelid)));
//		note.getLabellist().remove(label);
//		when(noteRepo.save(note)).thenReturn(note);
//		
//		when(userRepo.findByEmail(email)).thenReturn(user);
//		assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
//		user.getNotelist().add(note);
//		when(userRepo.save(user)).thenReturn(user);
		
		Response response = labelService.updateLabel(labelid, token, labeldto);
		assertEquals(200, response.getStatus());
		
	}
	
	
	/**
	 * Method: Test Case to Delete Label
	 */
	@Test
	public void testDeleteLabel() {
		
		note.setId(noteid);
		label.setId(labelid);
		note.setEmailId(email);

		when(jwt.getEmailId(token)).thenReturn(email);
		when(labelRepo.findById(labelid)).thenReturn(optionalLabel);
		when(noteRepo.findById(noteid)).thenReturn(optionalNote);
		
		assertTrue(note.getId().equals(noteid));
		assertTrue(label.getId().equals(labelid));
		labelRepo.deleteById(labelid);

		assertThat(note.getLabellist().removeIf(data -> data.getId().equals(labelid)));
		note.getLabellist().remove(label);
		when(noteRepo.save(note)).thenReturn(note);
		
		when(userRepo.findByEmail(email)).thenReturn(user);
		assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
		user.getNotelist().add(note);
		when(userRepo.save(user)).thenReturn(user);
		
		Response response = labelService.deleteLabel(labelid, token);
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	 * Method: Test Case to Show All Labels
	 */
	@Test
	public void showLabels() {
		
		Response response =  labelService.showLabels();
		assertEquals(200, response.getStatus());
		
	}
	
	
	/**
	 * Method: Test Case to Find Label By Id
	 */
	@Test
	public void testFindLabelById() {

		when(jwt.getEmailId(token)).thenReturn(email);
		when(userRepo.findByEmail(email)).thenReturn(user);
		when(labelRepo.findById(labelid)).thenReturn(optionalLabel);
		
		Response response = labelService.findLabelById(labelid, token);
		assertEquals(200, response.getStatus());
	}
	
	
	/**
	 * Method: Test Case to Add Label to Note
	 */
	
	public void testAddLabelToNote() {

		user.setEmail(email);
		note.setId(noteid);
		note.setEmailId(noteid);
		label.setEmail(labelid);
		label.setId(labelid);

		when(jwt.getEmailId(token)).thenReturn(email);
		when(userRepo.findByEmail(email)).thenReturn(user);
		assertTrue(email.equals(user.getEmail()));
		
		when(noteRepo.findByEmailId(email)).thenReturn(notelist);
		when(labelRepo.findById(labelid)).thenReturn(optionalLabel);
		
//		assertTrue(note.getId().equals(noteid));
//		assertTrue(label.getId().equals(labelid));
		
		label.getNotelist().add(note);
		when(labelRepo.save(label)).thenReturn(label);
		
		note.getLabellist().add(label);
		when(noteRepo.save(note)).thenReturn(note);
		
//		assertThat(user.getNotelist().removeIf(data -> data.getId().equals(note.getId())));
//		user.getNotelist().add(note);
//		when(userRepo.save(user)).thenReturn(user);
		
		Response response = labelService.addLabelToNote(noteid, labelid, token);
		assertEquals(200, response.getStatus());
	}
}
