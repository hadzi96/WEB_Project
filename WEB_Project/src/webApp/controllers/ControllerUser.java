package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import webApp.entities.Card;
import webApp.entities.User;
import webApp.reqests.AddCardReq;
import webApp.reqests.AddOpReq;
import webApp.reqests.BlockReq;
import webApp.reqests.ChangePWReq;
import webApp.reqests.DelOpReq;
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
		if (res.message == null)
			res.message = res.cookie;
		return Response.ok(res.message).cookie(cookie).build();
	}

	@POST
	@Path("/addcard")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean addCard(AddCardReq req, @HeaderParam("Authorization") Cookie cookie) {
		return service.addCard(req, cookie.getValue());
	}

	@GET
	@Path("/getcards")
	@Produces("text/json")
	public List<Card> getCard(@HeaderParam("Authorization") Cookie cookie) {
		return service.getCard(cookie.getValue());
	}

	@GET
	@Path("/type")
	@Produces("text/json")
	public Object getType(@HeaderParam("Authorization") Cookie cookie) {
		try {
			return service.getType(cookie.getValue());
		} catch (Exception e) {
			return false;
		}
	}

	@GET
	@Path("/getlock")
	@Produces("text/json")
	public Integer getLock(@HeaderParam("Authorization") Cookie cookie) {
		try {
			return service.getLock(cookie.getValue());
		} catch (Exception e) {
			return -1;
		}
	}

	@POST
	@Path("/addoperater")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean addOperater(AddOpReq req, @HeaderParam("Authorization") Cookie cookie) {
		return service.addoperater(req, cookie.getValue());
	}

	@POST
	@Path("/deloperater")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean delOperater(DelOpReq req, @HeaderParam("Authorization") Cookie cookie) {
		return service.deloperater(req, cookie.getValue());
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
	public boolean block(BlockReq req, @HeaderParam("Authorization") Cookie cookie) {
		return service.block(req, cookie.getValue());
	}

}
