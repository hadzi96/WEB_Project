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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import webApp.entities.Card;
import webApp.entities.User;
import webApp.entities.req.AddCardReq;
import webApp.entities.req.AddOpReq;
import webApp.entities.req.BlockReq;
import webApp.entities.req.ChangePWReq;
import webApp.entities.req.Cookie;
import webApp.entities.req.DelOpReq;
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
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/json")
	public Response logIn(User user) {
		LoginResponse res = service.login(user);
		if (res.success == false)
			return null;
		NewCookie cookie = new NewCookie("WebProject", res.cookie);
		return Response.ok(res.message).cookie(cookie).build();
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
	public List<Card> getCard(Cookie req) {
		return service.getCard(req);
	}

	@POST
	@Path("/getlock")
	@Produces("text/json")
	@Consumes("application/json")
	public Integer getLock(Cookie req) {
		return service.getLock(req);
	}

	@POST
	@Path("/addoperater")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean addOperater(AddOpReq req) {
		return service.addoperater(req);
	}

	@POST
	@Path("/deloperater")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean delOperater(DelOpReq req) {
		return service.deloperater(req);
	}

	@POST
	@Path("/changepw")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean changePW(ChangePWReq req) {
		return service.changePW(req);
	}

	@POST
	@Path("/block")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean block(BlockReq req) {
		return service.block(req);
	}

}
