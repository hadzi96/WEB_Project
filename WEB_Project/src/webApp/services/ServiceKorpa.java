package webApp.services;

import webApp.dao.DAOKorpa;
import webApp.dao.DAOProvera;
import webApp.entities.Photo;
import webApp.entities.req.AddItemReq;

public class ServiceKorpa {

	DAOKorpa dao;
	DAOProvera daoProvera;

	public ServiceKorpa(DAOKorpa dao) {
		this.dao = dao;
		this.daoProvera = new DAOProvera();
	}

	public boolean addItem(AddItemReq req) {
		String username = daoProvera.getUsernamefromCookie(req.cookie);
		if (username == null)
			return false;

		Photo photo = dao.getPhoto(req.idSlike, req.rezolucija);
		if (photo == null)
			return false;

		// sad mozes da dodas photo u korpu
		return dao.addItem(username, photo, req.rezolucija);
	}

}
