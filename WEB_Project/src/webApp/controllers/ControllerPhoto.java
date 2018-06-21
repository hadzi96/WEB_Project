package webApp.controllers;

import java.util.Base64;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import webApp.entities.File;
import webApp.entities.Photo;
import webApp.reqests.OceniPhotoReq;
import webApp.reqests.OdobriReq;
import webApp.reqests.OpenItemReq;
import webApp.reqests.SearchItemReq;
import webApp.responses.OpenResponse;
import webApp.services.ServicePhoto;

@Stateless
@LocalBean
@Path("/photo")
public class ControllerPhoto {

	private ServicePhoto service;

	public ControllerPhoto() {
		this.service = new ServicePhoto();
	}

	@POST
	@Path("/search")
	@Produces("text/json")
	@Consumes("application/json")
	public List<Photo> search(SearchItemReq parameters, @HeaderParam("Authorization") Cookie cookie) {
		return service.search(parameters, cookie.getValue());
	}

	@POST
	@Path("/open")
	@Consumes("application/json")
	@Produces("text/json")
	public OpenResponse open(OpenItemReq parameters, @HeaderParam("Authorization") Cookie cookie) {
		try {
			Photo photo = service.open(parameters, cookie.getValue());
			return new OpenResponse(photo.id, photo.ime, photo.autor, photo.mesto, photo.rezolucije, photo.cene,
					photo.opis, photo.ocena);
		} catch (Exception e) {
			return null;
		}
	}

	@POST
	@Path("/send")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean send(@MultipartForm File file, @HeaderParam("Authorization") Cookie cookie) {
		return service.send(file, cookie.getValue());
	}

	@POST
	@Path("/getphoto")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public String getFile(OpenItemReq parameters, @HeaderParam("Authorization") Cookie cookie) {
		byte[] file = service.getPhoto(parameters, cookie.getValue());

		return Base64.getEncoder().encodeToString(file);
	}

	@GET
	@Path("/getneodobrene")
	@Produces("text/json")
	public List<Photo> getNeodobrene(@HeaderParam("Authorization") Cookie cookie) {
		return service.getNeodobrene(cookie.getValue());
	}

	@POST
	@Path("/odobri")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean odobri(OdobriReq req, @HeaderParam("Authorization") Cookie cookie) {
		return service.odobri(req, cookie.getValue());
	}

	@POST
	@Path("/oceni")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean oceni(OceniPhotoReq req, @HeaderParam("Authorization") Cookie cookie) {
		return service.oceni(req, cookie.getValue());
	}
}
