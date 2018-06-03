package webApp.services;

import java.util.ArrayList;
import java.util.List;

import webApp.dao.DAOProveraUser;
import webApp.dao.DAOTest;
import webApp.entities.DaoTest;
import webApp.entities.Test;
import webApp.entities.User;
import webApp.utils.UtilsMethods;

public class ServiceTest {
	DAOTest dao;
	DAOProveraUser daoProvera;

	public ServiceTest() {
		this.dao = new DAOTest();
		this.daoProvera = new DAOProveraUser();
	}

	public boolean send(Test test, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return false;
		if (!user.type.equals("kupac"))
			return false;
		if (dao.getTest(user.username) != null)
			return false;
		if (test.check() == false)
			return false;

		ArrayList<String> filenames = new ArrayList<>();
		for (byte[] photo : test.photos) {
			String fileName = user.username + test.photos.indexOf(photo);
			filenames.add(fileName);
			UtilsMethods.saveTestPhoto(photo, fileName);
		}
		
		return dao.addPhoto(user.username, filenames);
	}

	public List<DaoTest> getNeocenjene(String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return null;
		if (!user.type.equals("operater"))
			return null;

		return dao.getNeocenjene();
	}

	public boolean oceniTest(String username, double ocena, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return false;
		if (!user.type.equals("operater")) {
			return false;
		}
		if (dao.getTest(username) == null) {
			return false;
		}
		if (ocena < 0 || ocena > 1) {
			return false;
		}

		return dao.oceniTest(username, ocena);
	}

	public byte[] getTestPhoto(String fileName, String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null)
			return null;
		if (!user.type.equals("operater"))
			return null;

		String filePath = "D:/Tests/" + fileName + ".png";
		return UtilsMethods.readFile(filePath);
	}

}
