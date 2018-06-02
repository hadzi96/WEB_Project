package webApp.services;

import java.util.ArrayList;
import java.util.List;

import webApp.dao.DAOKorpa;
import webApp.dao.DAOPhoto;
import webApp.dao.DAOProveraUser;
import webApp.entities.Item;
import webApp.entities.Photo;
import webApp.entities.req.AddItemReq;
import webApp.entities.req.BuyReq;
import webApp.entities.req.Cookie;
import webApp.utils.EmailSender;

public class ServiceKorpa {

	DAOKorpa dao;
	DAOProveraUser daoProvera;
	DAOPhoto daoPhoto;

	public ServiceKorpa() {
		this.dao = new DAOKorpa();
		this.daoProvera = new DAOProveraUser();
		this.daoPhoto = new DAOPhoto();
	}

	public boolean addItem(AddItemReq req) {
		String username = daoProvera.getUser(req.cookie).username;
		if (username == null)
			return false;

		Photo photo = daoPhoto.getPhoto(req.idSlike, req.rezolucija);
		if (photo == null)
			return false;

		if (photo.onSale == false)
			return false;

		Double cena = null;
		String[] rez = photo.rezolucije.split(";");
		String[] cene = photo.cene.split(";");
		for (int i = 0; i < rez.length; i++) {
			if (rez[i].equals(req.rezolucija)) {
				cena = Double.parseDouble(cene[i]);
			}
		}

		if (cena == null)
			return false;

		// sad mozes da dodas photo u korpu
		return dao.addItem(username, photo, req.rezolucija, cena);
	}

	public List<Item> getKorpa(Cookie req) {
		String username = daoProvera.getUser(req.cookie).username;
		if (username == null)
			return null;

		return dao.getKorpa(username);
	}

	public boolean buy(BuyReq req) {
		String username = daoProvera.getUser(req.cookie).username;
		if (username == null)
			return false;

		if (req.creditCard < 1000000000000000L || req.creditCard > 9999999999999999L) {
			return false;
		}

		List<Item> items = dao.getKorpa(username);
		List<String> sellerEmal = new ArrayList<>();
		List<String> itemName = new ArrayList<>();
		List<String> itemResolution = new ArrayList<>();

		for (Item i : items) {
			Photo photo = daoPhoto.getPhoto(i.idSlike, i.rezolucija);
			String autor = photo.autor;
			String email = daoProvera.getUserbyName(autor).email;
			sellerEmal.add(email);
			itemName.add(photo.ime);
			itemResolution.add(i.rezolucija);
		}

		if (dao.buy(username, req.optimisticLock) == false)
			return false;

		// ovde (saljeno slike na email)
		for (int i = 0; i < sellerEmal.size(); i++) {
			System.out.println(sellerEmal.get(i));
			EmailSender.informSeller(sellerEmal.get(i), itemName.get(i), itemResolution.get(i));
		}

		return true;
	}

}
