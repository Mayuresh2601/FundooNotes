package com.bridgelabz.fundoonotes.label.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LabelDTO {
	
	@NotBlank(message = "Label Title is Mandatory")
	private String labelTitle;

}
