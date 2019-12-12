/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class To Encrypt the Password
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Config {
	
	
	/**Method: To Encrypt the password at backend
	 * @return Encrypting Password
	 */
	@Bean
	public BCryptPasswordEncoder BCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	/**Method: To Map the Model with DTO class
	 * @return Mapping Models
	 */
	@Bean
	public ModelMapper mapper() {
		
		return new ModelMapper();
	}

}