package webApp.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import webApp.dao.DAOPhoto;
import webApp.dao.DAOProveraUser;
import webApp.entities.File;
import webApp.entities.Photo;
import webApp.entities.User;
import webApp.reqests.OdobriReq;
import webApp.reqests.OpenItemReq;
import webApp.reqests.SearchItemReq;
import webApp.utils.CookieMethods;
import webApp.utils.UtilsMethods;

public class ServicePhoto {
	DAOPhoto dao;
	DAOProveraUser daoProvera;
	String[] searchO = { "ime", "autor", "kategorija", "keyword" };
	String[] filterO = { "datumR", "datumS", "brProdajeR", "brProdajeS", "ceneR", "ceneS", "imeR", "imeS", "ocenaR",
			"ocenaS" };
	List<String> searchOptions = Arrays.asList(searchO);
	List<String> filterOptions = Arrays.asList(filterO);

	public ServicePhoto() {
		this.dao = new DAOPhoto();
		this.daoProvera = new DAOProveraUser();
	}

	public List<Photo> search(SearchItemReq parameters, String cookie) {
		if (CookieMethods.getUsrnameFromCookie(cookie) == null) {
			return null;
		}

		int offst = UtilsMethods.saftyConversionInt(parameters.offset);

		// search AND filter
		if (parameters.search != null && searchOptions.contains(parameters.search) && parameters.filter != null
				&& filterOptions.contains(parameters.filter)) {
			System.out.println("SEARCH AND FILTER");
			return dao.search(UtilsMethods.searchAndFilterStatement(parameters.search, parameters.searchValue,
					parameters.filter, offst));
		}

		// search
		if (parameters.search != null && searchOptions.contains(parameters.search)
				&& (parameters.filter == null || !filterOptions.contains(parameters.filter))) {
			System.out.println("SEARCH");
			return dao.search(UtilsMethods.searchStatement(parameters.search, parameters.searchValue, offst));
		}

		// filter
		if (parameters.filter != null && filterOptions.contains(parameters.filter)
				&& (parameters.search == null || !searchOptions.contains(parameters.search))) {
			System.out.println("FILTER");
			return dao.search(UtilsMethods.filterStatement(parameters.filter, offst));
		}

		// getAll
		System.out.println("GET ALL");
		return dao.search(UtilsMethods.getAllStatement(offst));
	}

	// open just one (by id)
	public Photo open(OpenItemReq parameters, String cookie) {
		if (CookieMethods.getUsrnameFromCookie(cookie) == null)
			return null;

		return dao.search(UtilsMethods.openStatement(parameters.id)).get(0);
	}

	public boolean send(File file, String cookie) {
		try {
			User user = daoProvera.getUser(cookie);
			if (user == null)
				return false;

			if (!user.type.equals("prodavac"))
				return false;

			if (UtilsMethods.checkCeneAndRez(file.photo.cene, file.photo.rezolucije) == false) {
				return false;
			}

			List<Photo> today = dao.getPhotosFromToday(user.username);
			if (today != null) {
				if (today.size() >= 3)
					return false;
			}

			List<Photo> week = dao.getPhotosFromWeek(user.username);
			if (week != null) {
				if (week.size() >= 8)
					return false;
			}

			file.photo.autor = user.username;
			file.photo.brProdaje = 0;

			// proveri dal je dovoljno velika slika za date rezolucije
			if (UtilsMethods.checkResolutions(file) == false)
				return false;

			String fileName = user.username + file.photo.ime + System.currentTimeMillis() / 1000;
			UtilsMethods.savePicture(file.getData(), fileName);
			file.photo.fileName = fileName;
			dao.addPhoto(file.photo);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public byte[] getPhoto(OpenItemReq parameters, String cookie) {
		if (CookieMethods.getUsrnameFromCookie(cookie) == null)
			return null;

		Photo photo = dao.getPhoto(parameters.id);
		if (photo == null)
			return null;

		String filePath = "D:/Photos/" + photo.fileName + ".png";
		byte[] img = UtilsMethods.readFile(filePath);

		// konvertuj u malu rezoluciju pre nego sto posaljes
		img = UtilsMethods.scaleImage(img, 100, 100);

		return img;
	}

	public List<Photo> getNeodobrene(String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return null;

		if (!user.type.equals("operater"))
			return null;

		return dao.getNeodobrene();
	}

	public boolean odobri(OdobriReq req, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return false;

		if (!user.type.equals("operater"))
			return false;

		return dao.odobri(req.id);
	}
}
