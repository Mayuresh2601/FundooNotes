package com.bridgelabz.fundoonotes.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateDTO {
	
	@NotBlank(message = "FirstName is Mandatory")
	private String firstName;

	@NotBlank(message = "LastName is Mandatory")
	private String lastName;
	
	@NotBlank(message = "Mobile Number is Mandatory")
	@Size(min = 10,max = 10, message = "Number Should be 10 Digit")
	private String mobileNumber;

}
