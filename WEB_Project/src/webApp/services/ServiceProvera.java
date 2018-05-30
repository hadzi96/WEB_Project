package webApp.services;

import webApp.dao.DAOProvera;

public class ServiceProvera {

	DAOProvera dao;

	public ServiceProvera(DAOProvera dao) {
		this.dao = dao;
	}

	public boolean hasCookie(String cookie) {
		if (cookie == null || cookie.length() < 100)
			return false;

		return dao.hasCookie(cookie);
	}
}
