package com.bridgelabz.fundoonotes.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ResetDTO {
	
	@NotBlank(message = "New Password is Mandatory")
	@Size(min = 6, max = 16,message = "Password should be Between 6 to 16 letters")
	private String newPassword;
	
	@NotBlank(message = "Confirm Password is Mandatory")
	@Size(min = 6, max = 16,message = "Password should be Between 6 to 16 letters")
	private String confirmPassword;

}
