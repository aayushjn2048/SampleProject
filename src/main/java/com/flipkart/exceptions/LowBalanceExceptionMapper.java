/**
 * 
 */
package com.flipkart.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import io.dropwizard.jersey.errors.ErrorMessage;

/**
 * @author HP
 *
 */
@Provider
public class LowBalanceExceptionMapper implements ExceptionMapper<LowBalanceException>{
	public Response toResponse(LowBalanceException exception) {
		return Response
	      .status(Status.NOT_ACCEPTABLE)
	      .entity(new ErrorMessage(406 , exception.getMessage()))
	      .type(MediaType.APPLICATION_JSON_TYPE)
	      .build();
	}

}



