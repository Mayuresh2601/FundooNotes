package com.bridgelabz.fundoonotes.note.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NoteDTO {
	
	@NotBlank(message = "Title is Mandatory")
	private String title;
	
	@NotBlank(message = "Description is Mandatory" )
	private String description;

}
