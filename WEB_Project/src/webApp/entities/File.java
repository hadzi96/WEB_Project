package webApp.entities;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jndi.cosnaming.IiopUrl.Address;

public class File {
	public Photo photo;

	public File() {
	}

	private byte[] data;

	public byte[] getData() {
		return data;
	}

	@FormParam("data")
	@PartType("application/octet-stream")
	public void setData(byte[] data) {
		this.data = data;
	}


	@FormParam("photo")
	@PartType("application/json")
	public void setPhoto(String json) {
		Gson gson = new GsonBuilder().create();
		photo = gson.fromJson(json, Photo.class);
	}
}
