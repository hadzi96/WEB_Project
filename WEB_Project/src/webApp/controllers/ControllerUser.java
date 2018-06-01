package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import webApp.entities.Card;
import webApp.entities.User;
import webApp.entities.req.AddCardReq;
import webApp.entities.req.GetReq;
import webApp.responses.LoginResponse;
import webApp.services.ServiceUser;

@Stateless
@LocalBean
@Path("/user")
public class ControllerUser {

	private ServiceUser service;

	public ControllerUser() {
		this.service = new ServiceUser();
	}

	@POST
	@Path("/register")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean register(User user) {
		return this.service.register(user);
	}

	@GET
	@Produces("text/json")
	@Path("/validation")
	public String validateUser(@QueryParam("token") String token) {
		if (this.service.validateUser(token)) {
			return "email: verified\nAccount: activated";
		} else {
			return "email verification error";
		}
	}

	@POST
	@Path("/login")
	@Produces("text/json")
	@Consumes("application/json")
	public LoginResponse logIn(User user) {
		return this.service.login(user);
	}

	@POST
	@Path("/addcard")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean addCard(AddCardReq req) {
		return service.addCard(req);
	}

	@POST
	@Path("/getcards")
	@Produces("text/json")
	@Consumes("application/json")
	public List<Card> getCard(GetReq req) {
		return service.getCard(req);
	}

	@POST
	@Path("/getlock")
	@Produces("text/json")
	@Consumes("application/json")
	public Integer getLock(GetReq req) {
		return service.getLock(req);
	}

}
