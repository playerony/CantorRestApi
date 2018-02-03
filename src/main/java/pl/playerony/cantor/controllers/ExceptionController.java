package pl.playerony.cantor.controllers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.playerony.cantor.exceptions.CantorRestApiException;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(CantorRestApiException.class)
	public Response handleDatabaseException(CantorRestApiException e) {
		return Response.status(404)
				       .entity(new Error(e.getMessage()))
				       .type(MediaType.APPLICATION_JSON)
				       .build();
	}
	
}
