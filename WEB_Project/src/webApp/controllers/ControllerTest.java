package webApp.controllers;

import java.util.Base64;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import webApp.entities.DaoTest;
import webApp.entities.File;
import webApp.entities.Photo;
import webApp.entities.Test;
import webApp.reqests.GetPhotoReq;
import webApp.reqests.OceniReq;
import webApp.reqests.OdobriReq;
import webApp.reqests.OpenItemReq;
import webApp.reqests.SearchItemReq;
import webApp.responses.OpenResponse;
import webApp.services.ServicePhoto;
import webApp.services.ServiceTest;

@Stateless
@LocalBean
@Path("/test")
public class ControllerTest {

	private ServiceTest service;

	public ControllerTest() {
		this.service = new ServiceTest();
	}

	@POST
	@Path("/send")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean send(@MultipartForm Test test, @CookieParam("WebProject") Cookie cookie) {
		return service.send(test, cookie.getValue());
	}

	@GET
	@Path("/getneocenjene")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DaoTest> getNeocenjene(@CookieParam("WebProject") Cookie cookie) {
		return service.getNeocenjene(cookie.getValue());
	}

	@POST
	@Path("/oceni")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean send(OceniReq req, @CookieParam("WebProject") Cookie cookie) {

		return service.oceniTest(req.username, req.ocena, cookie.getValue());
	}

	@POST
	@Path("/getphoto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getPhoto(GetPhotoReq req, @CookieParam("WebProject") Cookie cookie) {
		byte[] file = service.getTestPhoto(req.fileName, cookie.getValue());

		return Base64.getEncoder().encodeToString(file);
	}

}
