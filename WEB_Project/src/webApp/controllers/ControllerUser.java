package webApp.controllers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import webApp.dao.DAOUser;
import webApp.entities.User;
import webApp.services.ServiceUser;

@Stateless
@LocalBean
@Path("/user")
public class ControllerUser {

	private ServiceUser service;

	public ControllerUser() {
		this.service = new ServiceUser(new DAOUser());
	}

	@POST
	@Path("/register")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean register(User user) {
		return this.service.register(user);
	}

	@POST
	@Path("/login")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean logIn(User user) {
		return this.service.login(user);
	}

	@GET
	@Produces("text/json")
	@Path("/validation")
	public boolean validateUser(@QueryParam("token") String token) {
		return this.service.validateUser(token);
	}
}
