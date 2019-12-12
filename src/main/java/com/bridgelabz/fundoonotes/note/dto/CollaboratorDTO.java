package com.bridgelabz.fundoonotes.note.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CollaboratorDTO {
	
	@NotBlank(message = "Collaborator Email is Mandatory")
	private String collaboratorEmail;

}
