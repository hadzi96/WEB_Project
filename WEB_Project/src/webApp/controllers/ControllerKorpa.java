package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import webApp.dao.DAOKorpa;
import webApp.entities.Item;
import webApp.entities.req.AddItemReq;
import webApp.entities.req.BuyReq;
import webApp.entities.req.GetKorpaReq;
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
	public boolean addItem(AddItemReq req) {
		return service.addItem(req);
	}

	@POST
	@Path("/getKorpa")
	@Produces("text/json")
	@Consumes("application/json")
	public List<Item> getKorpa(GetKorpaReq req) {
		return service.getKorpa(req);
	}

	@POST
	@Path("/buy")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean buy(BuyReq req) {
		return service.buy(req);
	}

}
