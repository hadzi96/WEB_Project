package webApp.services;

import webApp.dao.DAOUser;
import webApp.entities.User;
import webApp.responses.LoginResponse;
import webApp.utils.EmailSender;
import webApp.utils.UtilsMethods;

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
			String token = UtilsMethods.generateToken(60);

			if (this.dao.register(user, token)) {
				EmailSender.sendVerification(user.email, token);
				return true;
			}
		}

		return false;
	}

	public boolean validateUser(String token) {
		if (token.equals("utilized"))
			return false;

		return this.dao.validateUser(token);
	}

	public LoginResponse login(User user) {
		if (user.username == null || user.username.equals("") || user.password == null || user.password.equals(""))
			return new LoginResponse(false);

		return this.dao.login(user);
	}

}
