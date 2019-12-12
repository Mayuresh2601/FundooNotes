package com.bridgelabz.fundoonotes.note.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoonotes.label.model.Label;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document
@Data
public class Note {
	
	@Id
	private String id;
	
	@NotBlank(message= "Title is Mandatory")
	private String title;
	
	@NotBlank(message = "Description is Mandatory")
	private String description;
	
	@NotBlank(message = "Email ID is Mandatory")
	@Pattern(regexp=".+@.+\\.[a-z]+", message = "EmailId Format is Invalid")
	private String emailId;
	
	@NotBlank(message = "Created Note Date is Mandatory")
	private String createDate;
	
	private String editDate;
	
	private boolean isPin;
	
	private boolean isTrash;
	
	private boolean isArchieve;

	@JsonIgnore
	@DBRef(lazy = true)
	List<Label> labellist = new ArrayList<>();
	
	List<String> collaboratorList = new ArrayList<>();
	
	private String reminder;
//	
//	public Note(String id, @NotBlank(message = "Title is Mandatory") String title,
//			@NotBlank(message = "Description is Mandatory") String description,
//			@NotBlank(message = "Email ID is Mandatory") @Pattern(regexp = ".+@.+\\.[a-z]+", message = "EmailId Format is Invalid") String emailId,
//			@NotBlank(message = "Created Note Date is Mandatory") String createDate, String editDate, boolean isPin,
//			boolean isTrash, boolean isArchieve, List<Label> labellist, List<String> collaboratorList,
//			String reminder) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.description = description;
//		this.emailId = emailId;
//		this.createDate = createDate;
//		this.editDate = editDate;
//		this.isPin = isPin;
//		this.isTrash = isTrash;
//		this.isArchieve = isArchieve;
//		this.labellist = labellist;
//		this.collaboratorList = collaboratorList;
//		this.reminder = reminder;
//	}

}
