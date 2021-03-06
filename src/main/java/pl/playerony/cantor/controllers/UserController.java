package pl.playerony.cantor.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.playerony.cantor.database.models.User;
import pl.playerony.cantor.database.services.UserService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Component
@CrossOrigin
@Path("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Response insertUser(User user) throws CantorRestApiException {
		if(user != null && user.getPassword() != null)
			user.setPassword(encoder.encode(user.getPassword()));
		
		userService.insertUser(user);
		
		return Response.status(200)
				       .entity(user)
				       .build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Response updateUser(@PathParam("id") Long userId, User user) throws CantorRestApiException {
		if(user != null && user.getPassword() != null)
			user.setPassword(encoder.encode(user.getPassword()));
		
		userService.updateUser(userId, user);
		
		return Response.status(200)
				       .entity(user)
				       .build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	//@PreAuthorize("hasAnyRole('ADMIN')")
	public Response removeUser(@PathParam("id") Long userId) throws CantorRestApiException {
		userService.removeUser(userId);
		
		return Response.status(200)
				       .build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Response fetchUserByUsername(@PathParam("id") Long userId) throws CantorRestApiException {
		User foundUser = userService.fetchUserByUserId(userId);
		
		return Response.status(200)
					   .entity(foundUser)
				       .build();
	}
}
