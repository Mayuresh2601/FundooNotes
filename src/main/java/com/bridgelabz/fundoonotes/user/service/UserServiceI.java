package com.bridgelabz.fundoonotes.user.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.user.dto.ForgetDTO;
import com.bridgelabz.fundoonotes.user.dto.LoginDTO;
import com.bridgelabz.fundoonotes.user.dto.RegisterDTO;
import com.bridgelabz.fundoonotes.user.dto.ResetDTO;
import com.bridgelabz.fundoonotes.user.dto.UpdateDTO;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.user.response.Response;

public interface UserServiceI {
	
	public Response createUser(RegisterDTO regdto);
	
	public Response findUser(String token);
	
	public Response showUsers();
	
	public Response deleteUser(String id);
	
	public Response updateUser(UpdateDTO updatedto, String token);

	public Response login(LoginDTO logindto, String token);
	
	public Response forgetPassword(ForgetDTO forget);
	
	public Response resetPassword(ResetDTO reset, String token);
	
	public Response verify(String token);
	
	public Response uploadProfilePicture(String token, MultipartFile file) throws IOException;
	
	public Response updateProfilePicture(String token, MultipartFile file) throws IOException;
	
	public Response removeProfilePicture(String token);
}
