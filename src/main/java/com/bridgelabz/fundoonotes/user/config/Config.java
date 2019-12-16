/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class To Configure Maven Project
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
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

	
	/**Method: To Implement Swagger On UserInterface
	 * @return SwaggerUI
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}