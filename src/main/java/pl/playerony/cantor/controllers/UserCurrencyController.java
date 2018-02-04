package pl.playerony.cantor.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.playerony.cantor.database.models.UserCurrency;
import pl.playerony.cantor.database.services.UserCurrencyService;
import pl.playerony.cantor.exceptions.CantorRestApiException;

@Component
@CrossOrigin
@Path("/userCurrency")
public class UserCurrencyController {
	@Autowired
	private UserCurrencyService userCurrencyService;
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertUserCurrency(UserCurrency userCurrency) throws CantorRestApiException {
		userCurrencyService.insertUserCurrency(userCurrency);
		
		return Response.status(200)
				       .entity(userCurrency)
				       .build();
	}
	
	@POST
	@Path("/buy")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response buyUserCurrency(UserCurrency userCurrency) throws CantorRestApiException {
		Long userCurrencyId = userCurrency.getUserCurrencyId();
		Integer userCurrencyAmount = userCurrency.getCurrencyAmount();
		
		userCurrencyService.buyCurrency(userCurrencyId, userCurrencyAmount);
		
		return Response.status(200)
				       .entity(userCurrency)
				       .build();
	}
	
	@POST
	@Path("/sell")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sellUserCurrency(UserCurrency userCurrency) throws CantorRestApiException {
		Long userCurrencyId = userCurrency.getUserCurrencyId();
		Integer userCurrencyAmount = userCurrency.getCurrencyAmount();
		
		userCurrencyService.sellCurrency(userCurrencyId, userCurrencyAmount);
		
		return Response.status(200)
				       .entity(userCurrency)
				       .build();
	}
	
	@GET
	@Path("/all/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchUserCurrencies(@PathParam("id") Long userId) throws CantorRestApiException {
		List<UserCurrency> userCurrencies = userCurrencyService.fetchUserCurrenciesByUserId(userId);
		
		return Response.status(200)
				       .entity(userCurrencies)
				       .build();
	}
	
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchUserCurrency(@PathParam("id") Long userCurrencyId) throws CantorRestApiException {
		UserCurrency userCurrency = userCurrencyService.fetchUserCurrencyByUserCurrencyId(userCurrencyId);
		
		return Response.status(200)
				       .entity(userCurrency)
				       .build();
	}
	
}
