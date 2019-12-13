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
	private List<Label> labellist = new ArrayList<>();
	
	private List<String> collaboratorList = new ArrayList<>();
	
	private String reminder;

}
