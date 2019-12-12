package com.bridgelabz.fundoonotes.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterDTO {

	@NotBlank(message = "First Name is Mandatory")
	private String firstName;
	
	@NotBlank(message = "Last Name is Mandatory")
	private String lastName;

	@NotBlank(message = "Email Id is Mandotary")
	@Pattern(regexp=".+@.+\\.[a-z]+", message = "EmailId Format is Invalid")
	private String email;

	@NotBlank(message = "Password is Mandatory")
	@Size(min = 6,max = 16,message = "Password should be Between 6 to 16 letters")
	private String password;
	
	@NotBlank(message = "Confirm Password is Mandatory")
	@Size(min = 6,max = 16,message = "Password should be Between 6 to 16 letters")
	private String confirmPassword;
	
	@NotBlank(message = "Mobile Number is Mandatory")
	private String mobileNumber;
	
	private boolean isValidate;

}
