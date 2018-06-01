package webApp.services;

import webApp.dao.DAOProveraUser;

public class ServiceProveraUser {

	DAOProveraUser dao;

	public ServiceProveraUser(DAOProveraUser dao) {
		this.dao = dao;
	}

	public boolean hasCookie(String cookie) {
		if (cookie == null || cookie.length() < 100)
			return false;

		return dao.hasCookie(cookie);
	}
}
