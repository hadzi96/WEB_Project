package webApp.entities;

import java.util.ArrayList;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

public class Test {

	public byte[] photo1;
	public byte[] photo2;
	public byte[] photo3;
	public byte[] photo4;
	public byte[] photo5;
	public byte[] photo6;
	public byte[] photo7;
	public byte[] photo8;
	public byte[] photo9;
	public byte[] photo10;

	public ArrayList<byte[]> photos = new ArrayList<>();

	@FormParam("photo1")
	@PartType("application/octet-stream")
	public void setPhoto1(byte[] photo) {
		photo1 = photo;
	}

	@FormParam("photo2")
	@PartType("application/octet-stream")
	public void setPhoto2(byte[] photo) {
		photo2 = photo;
	}

	@FormParam("photo3")
	@PartType("application/octet-stream")
	public void setPhoto3(byte[] photo) {
		photo3 = photo;
	}

	@FormParam("photo4")
	@PartType("application/octet-stream")
	public void setPhoto4(byte[] photo) {
		photo4 = photo;
	}

	@FormParam("photo5")
	@PartType("application/octet-stream")
	public void setPhoto5(byte[] photo) {
		photo5 = photo;
	}

	@FormParam("photo6")
	@PartType("application/octet-stream")
	public void setPhoto6(byte[] photo) {
		photo6 = photo;
	}

	@FormParam("photo7")
	@PartType("application/octet-stream")
	public void setPhoto7(byte[] photo) {
		photo7 = photo;
	}

	@FormParam("photo8")
	@PartType("application/octet-stream")
	public void setPhoto8(byte[] photo) {
		photo8 = photo;
	}

	@FormParam("photo9")
	@PartType("application/octet-stream")
	public void setPhoto9(byte[] photo) {
		photo9 = photo;
	}

	@FormParam("photo10")
	@PartType("application/octet-stream")
	public void setPhoto10(byte[] photo) {
		photo10 = photo;
	}

	public boolean check() {
		if (photo1 == null || photo1.length < 100)
			return false;
		if (photo2 == null || photo2.length < 100)
			return false;
		if (photo3 == null || photo3.length < 100)
			return false;
		if (photo4 == null || photo4.length < 100)
			return false;
		if (photo5 == null || photo5.length < 100)
			return false;
		if (photo6 == null || photo6.length < 100)
			return false;
		if (photo7 == null || photo7.length < 100)
			return false;
		if (photo8 == null || photo8.length < 100)
			return false;
		if (photo9 == null || photo9.length < 100)
			return false;
		if (photo10 == null || photo10.length < 100)
			return false;

		photos.add(photo1);
		photos.add(photo2);
		photos.add(photo3);
		photos.add(photo4);
		photos.add(photo5);
		photos.add(photo6);
		photos.add(photo7);
		photos.add(photo8);
		photos.add(photo9);
		photos.add(photo10);

		return true;
	}

}
