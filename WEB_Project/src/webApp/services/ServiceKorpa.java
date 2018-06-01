package webApp.services;

import java.util.List;

import webApp.dao.DAOKorpa;
import webApp.dao.DAOPhoto;
import webApp.dao.DAOProveraUser;
import webApp.entities.Item;
import webApp.entities.Photo;
import webApp.entities.req.AddItemReq;
import webApp.entities.req.BuyReq;
import webApp.entities.req.GetReq;

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

		// sad mozes da dodas photo u korpu
		return dao.addItem(username, photo, req.rezolucija, req.optimisticLock);
	}

	public List<Item> getKorpa(GetReq req) {
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

		// ovde (saljeno slike na email)
		// obavestavamo prodavca o kupovini
		return dao.buy(username, req.optimisticLock);
	}

}
