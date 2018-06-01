package webApp.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import webApp.entities.File;
import webApp.entities.Photo;
import webApp.entities.req.GetReq;
import webApp.entities.req.OpenItemReq;
import webApp.entities.req.SearchItemReq;
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
	public List<Photo> open(OpenItemReq parameters) {
		return service.open(parameters);
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
	public Response getFile() {
		java.io.File file = null;
		if (file == null)
			return null;
		else
			return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
					.header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"").build();
	}
}
