package webApp.services;

import java.util.List;

import webApp.dao.DAOCard;
import webApp.dao.DAOProveraUser;
import webApp.dao.DAOUser;
import webApp.entities.Card;
import webApp.entities.User;
import webApp.entities.req.AddCardReq;
import webApp.entities.req.GetReq;
import webApp.responses.LoginResponse;
import webApp.utils.EmailSender;
import webApp.utils.UtilsMethods;

public class ServiceUser {
	DAOUser dao;
	DAOProveraUser daoProvera;
	DAOCard daoCard;

	public ServiceUser() {
		this.dao = new DAOUser();
		this.daoProvera = new DAOProveraUser();
		this.daoCard = new DAOCard();
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

	public boolean addCard(AddCardReq req) {
		String username = daoProvera.getUsernamefromCookie(req.cookie);
		if (username == null)
			return false;
		if (req.creditCard < 1000000000000000L || req.creditCard > 9999999999999999L) {
			return false;
		}

		return daoCard.addCard(username, req.creditCard);
	}

	public List<Card> getCard(GetReq req) {
		String username = daoProvera.getUsernamefromCookie(req.cookie);
		if (username == null)
			return null;

		return daoCard.getCards(username);
	}

	public Integer getLock(GetReq req) {
		String username = daoProvera.getUsernamefromCookie(req.cookie);
		if (username == null)
			return null;

		return dao.getLock(username);
	}

}
