/**
 * 
 */
package com.flipkart.resources;

import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.flipkart.api.UserApiImplementation;
import com.flipkart.api.UserApiInterface;
import com.flipkart.bean.Payment;
import com.flipkart.bean.User;
import com.flipkart.dao.UserEntityDao;
import com.flipkart.entity.PaymentEntity;
import com.flipkart.entity.UserEntity;
import com.flipkart.exceptions.LowBalanceException;
import com.flipkart.exceptions.SelfPaymentException;
import com.flipkart.exceptions.VersionConflictException;
import com.flipkart.exceptions.WrongCredentialsException;

import io.dropwizard.hibernate.UnitOfWork;

/**
 * @author HP
 *
 */
@Path("/users")
public class SampleResource {
	
	private final UserEntityDao sampleEntityDao;

    public SampleResource(UserEntityDao sampleEntityDao2) {
		this.sampleEntityDao = sampleEntityDao2;
	}

	@POST
    @Path("/register")
	@UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(UserEntity user) {
    	if(sampleEntityDao.registerUser(user))
    		return Response.status(Status.CREATED).entity("User is succesfully registered!!!").build();
		return Response.status(Status.BAD_REQUEST).entity("Error Occured").build();
    }
    
    @GET
    @Path("/login")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(@NotNull @QueryParam("username") String username, @NotNull  @QueryParam("password") String password) throws WrongCredentialsException {
    	sampleEntityDao.userAuthentication(username, password);
    	return Response.status(Status.FOUND).entity("User credentials verified").build();
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewUserList() {
    	System.out.println("Hello world");
    	ArrayList<UserEntity> data = sampleEntityDao.viewUserList();
    	if(data.size()!=0)
    		return Response.status(Status.CREATED).entity(data).build();
		return Response.status(Status.BAD_REQUEST).entity("Error Occured").build();
    }
    
    @GET
    @Path("/getUser")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewUserData(@NotNull @QueryParam("userId") Integer userId) throws WrongCredentialsException {
    	UserEntity data = sampleEntityDao.viewUserData(userId);
    	if(data!=null)
    		return Response.status(Status.CREATED).entity(data).build();
		return Response.status(Status.BAD_REQUEST).entity("Error Occured").build();
    }
    
    @POST
    @Path("/payment")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makePayment(PaymentEntity payment) throws LowBalanceException {
    	sampleEntityDao.makePayment(payment);
		return Response.status(Status.BAD_REQUEST).entity("Payment Successful").build();
    }
    
    @POST
    @Path("/paymentOp")
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makePaymentOp(PaymentEntity payment) throws LowBalanceException, VersionConflictException {
    	sampleEntityDao.makePaymentOp(payment);
		return Response.status(Status.BAD_REQUEST).entity("Payment Successful").build();
    }
    
    @GET
    @Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response hello() {
	    return Response.status(Status.OK).entity("Welcome To User API").build();
	}
}