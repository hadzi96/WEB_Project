package webApp.services;

import java.util.List;

import webApp.dao.DAOCard;
import webApp.dao.DAOProveraUser;
import webApp.dao.DAOUser;
import webApp.entities.Card;
import webApp.entities.User;
import webApp.reqests.AddCardReq;
import webApp.reqests.AddOpReq;
import webApp.reqests.BlockReq;
import webApp.reqests.ChangePWReq;
import webApp.reqests.DelOpReq;
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
		user.isActive = false;
		user.type = "kupac";

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

		LoginResponse res = dao.login(user);
		if (res.success)
			return res;
		User operater = daoProvera.getUser(user.username, user.password);
		if (operater == null)
			return res;
		if (operater.type.equals("operater") && operater.isActive == false) {
			res.message = "changePW";
		}

		return res;
	}

	public boolean addCard(AddCardReq req, String cookie) {
		String username = daoProvera.getUser(cookie).username;
		if (username == null)
			return false;
		if (req.creditCard < 1000000000000000L || req.creditCard > 9999999999999999L) {
			return false;
		}

		return daoCard.addCard(username, req.creditCard);
	}

	public List<Card> getCard(String cookie) {
		String username = daoProvera.getUser(cookie).username;
		if (username == null)
			return null;

		return daoCard.getCards(username);
	}

	public Object getType(String cookie) {
		return daoProvera.getUser(cookie).type;
	}

	public Integer getLock(String cookie) {
		String username = daoProvera.getUser(cookie).username;
		if (username == null)
			return null;

		return dao.getLock(username);
	}

	public boolean addoperater(AddOpReq req, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null || !user.type.equals("admin"))
			return false;

		User operater = new User();
		operater.username = req.username;
		operater.password = req.password;
		operater.email = req.email;
		operater.drzava = req.drzava;
		operater.isActive = false;
		operater.type = "operater";
		operater.activationToken = "utilized";
		operater.optimisticLock = 0;

		return dao.register(operater, "utilized");
	}

	public boolean deloperater(DelOpReq req, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null || !user.type.equals("admin"))
			return false;

		return dao.deleteUser(req.username);
	}

	public boolean changePW(ChangePWReq req) {
		User user = daoProvera.getUser(req.username, req.password);

		if (user == null)
			return false;

		if (!user.type.equals("operater"))
			return false;

		return dao.changePW(user.username, req.newpassword);
	}

	public boolean block(BlockReq req, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return false;

		if (!user.type.equals("operater"))
			return false;

		User bUser = daoProvera.getUserbyName(req.username);
		if (bUser == null)
			return false;

		if (bUser.type.equals("operater") || bUser.type.equals("admin"))
			return false;

		return dao.block(req.username);
	}

}
