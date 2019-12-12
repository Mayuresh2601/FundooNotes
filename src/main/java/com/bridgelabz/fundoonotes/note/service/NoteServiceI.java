package com.bridgelabz.fundoonotes.note.service;

import java.util.List;

import com.bridgelabz.fundoonotes.note.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.note.dto.NoteDTO;
import com.bridgelabz.fundoonotes.note.model.Note;
import com.bridgelabz.fundoonotes.user.response.Response;

public interface NoteServiceI {
	
	public Response createNote(String token, NoteDTO notedto);
	
	public Response updateNote(String noteid, String token, NoteDTO notedto);
	
	public Response deleteNote(String noteid, String token);
	
	public Response findNoteById(String noteid, String token);
	
	public List<Note> showNotes();
	
	public Response isPin(String noteid, String token);
	
	public Response isTrash(String noteid, String token);
	
	public Response isArchieve(String noteid, String token);
	
	public List<Note> sortNoteByTitleAsc();
	
	public List<Note> sortNoteByTitleDesc();
	
	public List<Note> sortNoteByDateAsc();
	
	public List<Note> sortNoteByDateDesc();
	
	public Response addCollaboratorDemo(String noteid, String token);
	
	public Response addCollaborator(String noteid, String token, CollaboratorDTO collaboratorEmailId);

	public Response removeCollaborator(String noteid, String token, String collaboratorEmailId);
	
	public Response addReminder(String token,String noteid, int year, int month, int day, int hour, int minute, int second);
	
	public Response removeReminder(String token, String noteid);
	
	public Response editReminder(String token,String noteid, int year, int month, int day, int hour, int minute, int second);
}
