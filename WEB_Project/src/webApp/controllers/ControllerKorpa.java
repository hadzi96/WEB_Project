package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;

import webApp.dao.DAOKorpa;
import webApp.entities.Item;
import webApp.reqests.AddItemReq;
import webApp.reqests.BuyReq;
import webApp.services.ServiceKorpa;

@Stateless
@LocalBean
@Path("/korpa")
public class ControllerKorpa {

	private ServiceKorpa service;

	public ControllerKorpa() {
		this.service = new ServiceKorpa();
	}

	@POST
	@Path("/addItem")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean addItem(AddItemReq req, @CookieParam("WebProject") Cookie cookie) {
		return service.addItem(req, cookie.getValue());
	}

	@GET
	@Path("/getKorpa")
	@Produces("text/json")
	public List<Item> getKorpa(@CookieParam("WebProject") Cookie cookie) {
		return service.getKorpa(cookie.getValue());
	}

	@POST
	@Path("/buy")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean buy(BuyReq req, @CookieParam("WebProject") Cookie cookie) {
		return service.buy(req, cookie.getValue());
	}

}
