package webApp.controllers;

import java.util.Base64;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import webApp.entities.File;
import webApp.entities.Photo;
import webApp.entities.req.Cookie;
import webApp.entities.req.OdobriReq;
import webApp.entities.req.OpenItemReq;
import webApp.entities.req.SearchItemReq;
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
	public List<Photo> search(SearchItemReq parameters) {
		return service.search(parameters);
	}

	@POST
	@Path("/open")
	@Consumes("application/json")
	@Produces("text/json")
	public OpenResponse open(OpenItemReq parameters) {
		Photo photo = service.open(parameters);
		return new OpenResponse(photo.id, photo.mesto, photo.rezolucije, photo.cene, photo.opis, photo.ocena);
	}

	@POST
	@Path("/send")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean send(@MultipartForm File file) {
		return service.send(file);
	}

	@POST
	@Path("/getphoto")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public String getFile(OpenItemReq parameters) {
		byte[] file = service.getPhoto(parameters);

		return Base64.getEncoder().encodeToString(file);
	}

	@POST
	@Path("/getneodobrene")
	@Produces("text/json")
	@Consumes("application/json")
	public List<Photo> getNeodobrene(Cookie req) {
		return service.getNeodobrene(req);
	}

	@POST
	@Path("/odobri")
	@Produces("text/json")
	@Consumes("application/json")
	public boolean odobri(OdobriReq req) {
		return service.odobri(req);
	}
}
