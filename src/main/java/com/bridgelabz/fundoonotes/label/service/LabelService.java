package com.bridgelabz.fundoonotes.label.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.label.dto.LabelDTO;
import com.bridgelabz.fundoonotes.label.model.Label;
import com.bridgelabz.fundoonotes.label.repository.LabelRepositoryI;
import com.bridgelabz.fundoonotes.note.model.Note;
import com.bridgelabz.fundoonotes.note.repository.NoteRepositoryI;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.user.repository.UserRepositoryI;
import com.bridgelabz.fundoonotes.user.response.Response;
import com.bridgelabz.fundoonotes.user.utility.Jwt;

@Service
public class LabelService implements LabelServiceI{
	
	@Autowired
	private LabelRepositoryI labelrepository;
	
	@Autowired
	private NoteRepositoryI noterepository;
	
	@Autowired
	private UserRepositoryI userrepository;
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private Environment labelEnvironment;
	
	
	/**
	 *Method: To Create Label
	 */
	@Override
	public Response createLabel(String token, LabelDTO labeldto) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if (email.equals(user.getEmail())) {
			
			Label label = mapper.map(labeldto, Label.class);
			label.setLabelTitle(labeldto.getLabelTitle());
			label.setEmail(email);

			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			String date = now.format(formatter);

			label.setCreateDate(date);
			labelrepository.save(label);
			return new Response(labelEnvironment.getProperty("Status_200"), labelEnvironment.getProperty("Create_Label"), labelEnvironment.getProperty("CREATE_LABEL"));
		}
		return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);

	}

	
	/**
	 *Method: To Update Label
	 */
	@Override
	public Response updateLabel(String labelid, String token, LabelDTO labeldto) {

		String email = jwt.getEmailId(token);
		Label label = labelrepository.findById(labelid).get();

		if (email != null) {

			if (label.getId().equalsIgnoreCase(labelid)) {
				label.setLabelTitle(labeldto.getLabelTitle());

				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
				String date = now.format(formatter);

				label.setEditDate(date);
				labelrepository.save(label);

				//Extra Code 
//				n.getLabellist().removeIf(data -> data.getId().equals(labelid));
//				n.getLabellist().add(label);
//				noterepository.save(n);
//					
//				User user = userrepository.findByEmail(email);
//				user.getNotelist().removeIf(data -> data.getId().equals(n.getId()));
//				user.getNotelist().add(n);
//				userrepository.save(user);
				return new Response(labelEnvironment.getProperty("Status_200"), labelEnvironment.getProperty("Update_Label"), labelEnvironment.getProperty("UPDATE_LABEL"));
			}
			return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("LABEL_ID_NOT_FOUND"), null);
		}
		return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To Delete Label
	 */
	@Override
	public Response deleteLabel(String labelid, String token) {

		String email = jwt.getEmailId(token);
		Label label = labelrepository.findById(labelid).get();
		if (email != null) {

			if (label.getId().equalsIgnoreCase(labelid)) {
				labelrepository.deleteById(labelid);

				//Extra Code
//				List<Note> notelist = noteService.showNotes();
//				Note note = notelist.stream().filter(data -> data.getEmailId().equals(email)).findAny().orElse(null);
//				n.getLabellist().remove(label);
//				noterepository.save(n);
//
//				User user = userrepository.findByEmail(email);
//				user.getNotelist().removeIf(data -> data.getId().equals(n.getId()));
//				user.getNotelist().add(n);
//				userrepository.save(user);
				return new Response(labelEnvironment.getProperty("Status_200"), labelEnvironment.getProperty("Delete_Label"), labelEnvironment.getProperty("DELETE_LABEL"));
			}
			return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("LABEL_ID_NOT_FOUND"), null);
		}
		return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To Show All Labels
	 */
	@Override
	public Response showLabels() {
		
		List<Label> list = labelrepository.findAll();
		return new Response(labelEnvironment.getProperty("Status_200"), labelEnvironment.getProperty("Show_Labels"), list);
	}

	
	/**
	 *Method: To Find Label By Id
	 */
	@Override
	public Response findLabelById(String labelid, String token) {
		
		String email = jwt.getEmailId(token);
		Label label = labelrepository.findById(labelid).get();
		
		if(email != null) {
			return new Response(labelEnvironment.getProperty("Status_200"), labelEnvironment.getProperty("Find_Label"), label);
		}
		return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	
	 /**
     * Adding label into notes and notes into labellist
     */
    @Override
    public Response addLabelToNote(String noteid, String labelid, String token) {
    	
    	String emailId = jwt.getEmailId(token);
        User user = userrepository.findByEmail(emailId);
        if (emailId.equals(user.getEmail())) {
        	
            List<Note> listNote = noterepository.findByEmailId(emailId);
            Note note = listNote.stream().filter(i -> i.getId().equals(noteid)).findAny().orElse(null);
            Label label = labelrepository.findById(labelid).get();
            
            if (noteid != null) {

            	if(labelid != null) {
            		label.getNotelist().add(note);
            		labelrepository.save(label);
            		
            		note.getLabellist().add(label);
	                noterepository.save(note);
	                
	    			user.getNotelist().removeIf(data -> data.getId().equals(note.getId()));
	    			user.getNotelist().add(note);
	    			userrepository.save(user);
	                return new Response(labelEnvironment.getProperty("Status_200"), labelEnvironment.getProperty("Add_Label_To_Note"), labelEnvironment.getProperty("LABEL_ADDED_TO_NOTE"));
            	}
                return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("LABEL_ID_NOT_FOUND"), null);
            }
            return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("NOTE_ID_NOT_FOUND"), null);
        }
        return new Response(labelEnvironment.getProperty("Status_404"), labelEnvironment.getProperty("UNAUTHORIZED_USER"), null);

    }

}
