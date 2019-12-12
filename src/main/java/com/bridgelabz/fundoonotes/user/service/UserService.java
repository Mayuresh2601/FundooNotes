/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Record Service
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.user.dto.ForgetDTO;
import com.bridgelabz.fundoonotes.user.dto.LoginDTO;
import com.bridgelabz.fundoonotes.user.dto.RegisterDTO;
import com.bridgelabz.fundoonotes.user.dto.ResetDTO;
import com.bridgelabz.fundoonotes.user.dto.UpdateDTO;
import com.bridgelabz.fundoonotes.user.model.User;
import com.bridgelabz.fundoonotes.user.repository.UserRepositoryI;
import com.bridgelabz.fundoonotes.user.response.Response;
import com.bridgelabz.fundoonotes.user.utility.Jms;
import com.bridgelabz.fundoonotes.user.utility.Jwt;

@Service
@PropertySource("classpath:message.properties")
public class UserService implements UserServiceI{
	
	@Autowired
	private UserRepositoryI userrepository;
	
	@Autowired
	private Jwt jwt;
	
	@Autowired
	private Jms jms;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Environment userEnvironment;
	
	/**
	 *Method: To Add New User into Database
	 */
	@Override
	public Response createUser(RegisterDTO regdto) {
	
		User user = mapper.map(regdto, User.class);

		if (user != null) {
			List<User> userlist = userrepository.findAll();
			List<String> userEmail = new ArrayList<>();
			for (int i = 0; i < userlist.size(); i++) {
				userEmail.add(userlist.get(i).getEmail());
			}
			
			boolean isVerify = userEmail.contains(regdto.getEmail());
			if(isVerify) {
				return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("EMAILID_EXISTS"), null);
			}
			
			boolean passwordChecker = regdto.getPassword().equals(regdto.getConfirmPassword());
			if (passwordChecker) {
				user.setPassword(bCryptPasswordEncoder.encode(regdto.getPassword()));
				user.setConfirmPassword(bCryptPasswordEncoder.encode(regdto.getConfirmPassword()));
			} else {
				return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("PASSWORD_NOT_MATCH"), null);
			}

			String token = jwt.createToken(user.getEmail());
			jms.sendMail(user.getEmail(), token);
			userrepository.save(user);
			return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Add_User"), userEnvironment.getProperty("ADD_USER"));
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To find User By Id in Database
	 */
	@Override
	public Response findUser(String token) {
		String emailId = jwt.getEmailId(token);
		return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Find_User"), userrepository.findByEmail(emailId));
	}

	
	/**
	 *Method: Display All User Details Present in Database
	 */
	@Override
	public Response showUsers() {
		
		List<User> userlist = userrepository.findAll();
		return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Show_Users"), userlist);
	}

	
	/**
	 *Method: To Delete a User by its Id 
	 */
	@Override
	public Response deleteUser(String id) {
		
		userrepository.deleteById(id);
		return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Delete_User"), userEnvironment.getProperty("DELETE_USER"));
	}

	
	/**
	 *Method: Update User By its Id
	 */
	@Override
	public Response updateUser(UpdateDTO updatedto, String token) {
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		
		if(email != null){
			user.setFirstName(updatedto.getFirstName());
			user.setLastName(updatedto.getLastName());
			user.setMobileNumber(updatedto.getMobileNumber());
				
			userrepository.save(user);
			return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Update_User"), userEnvironment.getProperty("UPDATE_USER"));
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	
	/**
	 *Method: Login User if password matches the existing user record
	 */
	@Override
	public Response login(LoginDTO logindto, String token) {
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(logindto != null) {
				
			boolean isValid = bCryptPasswordEncoder.matches(logindto.getPassword(), user.getPassword());
			
			if(isValid) {	
				user.setValidate(true);
				userrepository.save(user);
				return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Login"), userEnvironment.getProperty("USER_LOGIN_SUCCESSFUL"));
			}
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	
	/**
	 *Method: To generate JWT token of emailId and send it on mail
	 */
	@SuppressWarnings("unused")
	@Override
	public Response forgetPassword(ForgetDTO forget) {
		
		List<User> userlist = userrepository.findAll();
		List<String> email = new ArrayList<>();
		for (int i = 0; i < userlist.size(); i++) {
			email.add(userlist.get(i).getEmail());
		}
		
		if(email != null) {
			User user = mapper.map(forget, User.class);
			String token = jwt.createToken(user.getEmail());
			System.out.println("Recieved token:::::::  "+token);
			jms.sendMail(user.getEmail(), token);
			return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("CHECK_YOUR_MAIL"), userEnvironment.getProperty("CHECK_YOUR_MAIL"));
		}	
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

	
	/**
	 *Method: To Reset your password using the obtained token from email
	 */
	@Override
	public Response resetPassword(ResetDTO resetdto, String token) {
		
		String email=jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email.equals(user.getEmail()))
		{
			user.setPassword(resetdto.getNewPassword());
			
			if(resetdto.getNewPassword().contentEquals(resetdto.getConfirmPassword()))
			{
				user.setPassword(bCryptPasswordEncoder.encode(resetdto.getNewPassword()));
				userrepository.save(user);
				return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Reset_Password"), userEnvironment.getProperty("PASSWORD_RESETED"));
			}
			return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("PASSWORD_NOT_MATCHED"), null);
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	/**
	 *Method: To Verify the EmailId and Password  
	 */
	@Override
	public Response verify(String token) {

			String email=jwt.getEmailId(token);
			User user = userrepository.findByEmail(email);
			
			if(email.equals(user.getEmail()))
			{
				user.setValidate(true);
				userrepository.save(user);
				return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Verify_User"), userEnvironment.getProperty("VERIFIED_EMAILID_PASSWORD"));
			}
			return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
		}


	/**
	 *Method: To Upload Profile Picture
	 */
	@SuppressWarnings("resource")
	@Override
	public Response uploadProfilePicture(String token, MultipartFile file) throws IOException {
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		if(email.equals(user.getEmail())) {
			
			File convertFile = new File("/home/admin1/Documents/"+file.getOriginalFilename());
			convertFile.createNewFile();
			
			boolean fileChecker = file.getOriginalFilename().contains(".jpeg") || file.getOriginalFilename().contains(".jpg") || file.getOriginalFilename().contains(".png");
			if(!file.isEmpty() && fileChecker) {
				
				FileOutputStream fout = new FileOutputStream(convertFile);
				String path = "/home/admin1/Documents/" + file.getOriginalFilename();
				
				if(user.getProfilePicture() == null) {
					user.setProfilePicture(path);
					userrepository.save(user);
		
					fout.write(file.getBytes());
					fout.close();
					return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Upload_Profile_Picture"), userEnvironment.getProperty("PROFILE_PICTURE_UPLOADED"));
				}
				
				if(user.getProfilePicture().equals(path)) {
					return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("PROFILE_PICTURE_EXISTED_EXCEPTION"), null);
				}
			}
			return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("FILE_INVALID"), null);
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}


	/**
	 *Method: To Update Profile Picture
	 */
	@SuppressWarnings("resource")
	@Override
	public Response updateProfilePicture(String token, MultipartFile file) throws IOException {
		
		String email = jwt.getEmailId(token);
		File convertFile = new File("/home/admin1/Documents/"+file.getOriginalFilename());
		convertFile.createNewFile();
		User user = userrepository.findByEmail(email);
		if(email.equals(user.getEmail()))
		{
			String photo = user.getProfilePicture();
			boolean checker = file.getOriginalFilename().contains(".jpeg") || file.getOriginalFilename().contains(".jpg") || file.getOriginalFilename().contains(".png");
			if(!file.isEmpty() && checker) {
				FileOutputStream fout = new FileOutputStream(convertFile);
				String path = "/home/admin1/Documents/" + file.getOriginalFilename();
				
				if(photo == null) {
					return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("PROFILE_NOT_FOUND_EXCEPTION"), null);
				}
				
				if(user.getProfilePicture().equals(path)) {
					return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("PROFILE_PICTURE_EXISTED_EXCEPTION"), null);
				}
				
				user.setProfilePicture(path);
				userrepository.save(user);
	
				fout.write(file.getBytes());
				fout.close();
				return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Update_Profile_Picture"), userEnvironment.getProperty("PROFILE_PICTURE_UPDATED"));
			}
			return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("FILE_INVALID"), null);
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}
	
	
	/**
	 *Method: To Remove Profile Picture
	 */
	@Override
	public Response removeProfilePicture(String token) {
		
		String email = jwt.getEmailId(token);
		User user = userrepository.findByEmail(email);
		
		if(email.equals(user.getEmail())) {
			
			if(user.getProfilePicture() == null) {
				return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("PROFILE_PICTURE_REMOVE_EXCEPTION"), null);
			}
			
			user.setProfilePicture(null);
			userrepository.save(user);
			return new Response(userEnvironment.getProperty("Status_200"), userEnvironment.getProperty("Remove_Profile_Picture"), userEnvironment.getProperty("PROFILE_PICTURE_DELETED"));
		}
		return new Response(userEnvironment.getProperty("Status_404"), userEnvironment.getProperty("UNAUTHORIZED_USER"), null);
	}

}

