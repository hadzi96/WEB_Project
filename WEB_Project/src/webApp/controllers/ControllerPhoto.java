package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import webApp.dao.DAOPhoto;
import webApp.entities.Photo;
import webApp.entities.Search;
import webApp.services.ServicePhoto;

@Stateless
@LocalBean
@Path("/photo")
public class ControllerPhoto {

	private ServicePhoto service;

	public ControllerPhoto() {
		this.service = new ServicePhoto(new DAOPhoto());
	}

	@POST
	@Path("/search")
	@Produces("text/json")
	@Consumes("application/json")
	public List<Photo> searchByName(Search parameters) {
		return service.search(parameters);
	}

}