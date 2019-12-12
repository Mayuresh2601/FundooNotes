/******************************************************************************
*  
*  Purpose: To Implement Fundoo Notes App
*  @class Json Web Token
*  @author  Mayuresh Sunil Sonar
*
******************************************************************************/
package com.bridgelabz.fundoonotes.user.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

@Component
public class Jwt {
	
	private static final String SECRET_KEY="SECRET";
	
	
	/**Method: To Generate token of the EmailId
	 * @param email
	 * @return JWT Token Created
	 */
	public String createToken(String email) {
		
		Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
		return JWT.create().withClaim("emailId", email).sign(algorithm);
	}
	
	
	/**Method: To Generate EmailId from the given token
	 * @param token
	 * @return EmailID Created
	 */
	public String getEmailId(String token) {
		
		Claim claim=JWT.require(Algorithm.HMAC256(SECRET_KEY)).build().verify(token).getClaim("emailId");
		return claim.asString();
	}

}