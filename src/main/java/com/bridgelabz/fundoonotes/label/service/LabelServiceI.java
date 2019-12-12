package com.bridgelabz.fundoonotes.label.service;

import com.bridgelabz.fundoonotes.label.dto.LabelDTO;
import com.bridgelabz.fundoonotes.user.response.Response;

public interface LabelServiceI {
	
	public Response createLabel(String token, LabelDTO labeldto);
	
	public Response updateLabel(String labelid, String token, LabelDTO labeldto);
	
	public Response deleteLabel(String labelid, String token);
	
	public Response showLabels();
	
	public Response findLabelById(String labelid, String token);
	
	public Response addLabelToNote(String noteId, String labelId, String token);

}
