package webApp.controllers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import webApp.services.ServiceStatistika;

@Stateless
@LocalBean
@Path("/statistika")
public class ControllerStatistika {

	private ServiceStatistika service;

	public ControllerStatistika() {
		this.service = new ServiceStatistika();
	}

	@GET
	@Path("/get")
	@Produces("text/json")
	public Object get(@HeaderParam("Authorization") Cookie cookie) {
		try {
			return service.get(cookie.getValue());
		} catch (Exception e) {
			return false;
		}
	}

}
