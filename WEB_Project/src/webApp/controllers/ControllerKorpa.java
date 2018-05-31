package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import webApp.dao.DAOKorpa;
import webApp.dao.DAOPhoto;
import webApp.entities.AddItemReq;
import webApp.entities.OpenParameter;
import webApp.entities.Photo;
import webApp.entities.SearchParameter;
import webApp.services.ServiceKorpa;
import webApp.services.ServicePhoto;

@Stateless
@LocalBean
@Path("/korpa")
public class ControllerKorpa {

	private ServiceKorpa service;

	public ControllerKorpa() {
		this.service = new ServiceKorpa(new DAOKorpa());
	}

	@POST
	@Path("/addItem")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean addItem(AddItemReq item) {
		return service.addItem(item);
	}

}
