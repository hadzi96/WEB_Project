package webApp.services;

import webApp.dao.DAOUser;
import webApp.entities.User;
import webApp.utils.EmailSender;

public class ServiceUser {
	DAOUser dao;

	public ServiceUser(DAOUser dao) {
		this.dao = dao;
	}

	public boolean register(User user) {

		if (user.username == null || user.username.equals("") || user.password == null || user.password.equals("")
				|| user.email == null || user.email.equals("") || user.drzava == null || user.drzava.equals("")) {
			return false;
		}

		if (!this.dao.contais(user.username)) {
			String token = "1234";

			if (this.dao.register(user, token)) {
				EmailSender.sendVerification(user.email, token);
				return true;
			}
		}

		return false;
	}

	public boolean login(User user) {
		if (user.username == null || user.username.equals("") || user.password == null || user.password.equals(""))
			return false;

		return this.dao.login(user.username, user.password); 
	}

	public boolean validateUser(String token) { 
		return this.dao.validateUser(token);
	}

}
