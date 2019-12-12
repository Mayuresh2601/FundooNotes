package com.bridgelabz.fundoonotes.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ForgetDTO {
	
	@NotBlank(message = "EmailId is Mandatory")
	@Pattern(regexp=".+@.+\\.[a-z]+", message = "EmailId Format is Invalid")
	private String email;

}
