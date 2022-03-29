/**
 * 
 */
package com.flipkart.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.dropwizard.jersey.errors.ErrorMessage;


/**
 * @author HP
 *
 */
@Provider
public class WrongCredentialsExceptionMapper implements ExceptionMapper<WrongCredentialsException>{

	public Response toResponse(WrongCredentialsException exception) {
		return Response
	      .status(404)
	      .entity(new ErrorMessage(404, exception.getMessage()))
	      .type(MediaType.APPLICATION_JSON_TYPE)
	      .build();
	}

}
