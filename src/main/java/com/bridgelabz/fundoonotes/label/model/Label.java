package com.bridgelabz.fundoonotes.label.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoonotes.note.model.Note;

import lombok.Data;

@Document
@Data
public class Label {
	
	@Id
	private String id;
	
	@NotEmpty(message = "Label Title is Mandatory")
	private String labelTitle;
	
	@Pattern(regexp = ".+@.\\.[a-z]")
	private String email;
	
	private String createDate;
	
	private String editDate;
	
	@DBRef
	List<Note> notelist = new ArrayList<>();

}
