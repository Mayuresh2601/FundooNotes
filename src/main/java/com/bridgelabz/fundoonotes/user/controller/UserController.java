/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Record Controller
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.user.dto.ForgetDTO;
import com.bridgelabz.fundoonotes.user.dto.LoginDTO;
import com.bridgelabz.fundoonotes.user.dto.RegisterDTO;
import com.bridgelabz.fundoonotes.user.dto.ResetDTO;
import com.bridgelabz.fundoonotes.user.dto.UpdateDTO;
import com.bridgelabz.fundoonotes.user.response.Response;
import com.bridgelabz.fundoonotes.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	/**Method: To Create User in Database
	 * @param regdto
	 * @return Create User implementation Logic
	 */
	@PostMapping("/createuser")
	public Response createUser(@Valid @RequestBody RegisterDTO regdto) {
		
		Response response = userService.createUser(regdto); 
		return response;
	}
	
	
	/**Method: To Find User By Id from database
	 * @param id
	 * @return Find User by Id implementation Logic
	 */
	@GetMapping("/user")
	public Response findUser(@RequestHeader String token) {
		
		Response response = userService.findUser(token);
		return response;
	}
	
	
	/**Method: To Update User in database
	 * @param regdto
	 * @param token
	 * @return Update User Details implementation Logic
	 */
	@PutMapping("/updateuser")
	public Response updateUser(@Valid @RequestBody UpdateDTO updatedto,@RequestHeader String token) {
		
		Response response = userService.updateUser(updatedto, token);
		return response;
	}
	
	
	/**Method: To Delete User from database
	 * @param id
	 * @return Delete user Implementation Logic
	 */
	@DeleteMapping("/deleteuser")
	public Response deleteUser(@RequestHeader String id) {
		
		Response response = userService.deleteUser(id);
		return response;
	}
	
	
	/**Method: To Show All Users present in database
	 * @return Display All Users Implementation Logic
	 */
	@GetMapping("/showusers")
	public Response showUsers() {

		Response response =  userService.showUsers();
		return response;
	}
	
	
	/**Method: To Login in database
	 * @param logindto
	 * @return Implementation Logic of login
	 */
	@PostMapping("/login")
	public Response login(@Valid @RequestBody LoginDTO logindto,@RequestHeader String token) {
		
		Response response =  userService.login(logindto, token);
		return response;
	}
	
	
	/**Method: To check forget password implementation
	 * @param registerdto
	 * @return Implementation Logic of forget password
	 */
	@PostMapping("/forget")
	public Response forgetPassword(@Valid @RequestBody ForgetDTO forget) {
		
		Response response = userService.forgetPassword(forget);
		return response;
	}
	
	
	/**Method: To check reset password implementation
	 * @param reset
	 * @param token
	 * @return Implementation Logic of reset password
	 */
	@PostMapping("/reset")
	public Response resetPassword(@Valid @RequestBody ResetDTO reset,@RequestHeader String token) {
		
		Response response = userService.resetPassword(reset, token);
		return response;
	}
	
	
	/**Method: To Verify EmailId and Password in the Database
	 * @param token
	 * @return Implementation Logic of verifying user
	 */
	@PostMapping("/verify")
	public Response verifyUser(@Valid @RequestHeader String token) {
		
		Response response = userService.verify(token);
		return response;
	}
	
	
	/**Method: To Upload Profile Picture
	 * @param token
	 * @param file
	 * @return Upload Profile Picture Implementation Logic
	 * @throws IOException
	 */
	@PostMapping("/uploadpicture")
	public Response uploadProfilePicture(@RequestHeader String token, @RequestParam MultipartFile file) throws IOException {
		
		Response response = userService.uploadProfilePicture(token, file);
		return response;
	}
	
	
	/**Method: To Update Profile Picture
	 * @param token
	 * @param file
	 * @return Update Profile Picture Implementation Logic
	 * @throws IOException
	 */
	@PutMapping("/updatepicture")
	public Response updateProfilePicture(@RequestHeader String token, @RequestParam MultipartFile file) throws IOException {
		
		Response response = userService.updateProfilePicture(token, file);
		return response;
	}
	
	
	/**Method: To Remove Profile Picture
	 * @param token
	 * @return Remove Profile Picture Implementation Logic
	 */
	@DeleteMapping("/removepicture")
	public Response removeProfilePicture(@RequestHeader String token) {
		
		Response response = userService.removeProfilePicture(token);
		return response;
	}
	
}
