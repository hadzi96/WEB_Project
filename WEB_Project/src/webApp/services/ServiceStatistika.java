package webApp.services;

import java.util.List;

import webApp.dao.DAOProveraUser;
import webApp.dao.DAOStat;
import webApp.entities.Stat;
import webApp.entities.User;
import webApp.responses.StatistikaResponse;

public class ServiceStatistika {

	DAOProveraUser daoProvera;
	DAOStat dao;

	public ServiceStatistika() {
		this.dao = new DAOStat();
		this.daoProvera = new DAOProveraUser();
	}

	public Object get(String cookie) {
		User user = daoProvera.getUser(cookie);
		if (user == null || !user.type.equals("operater"))
			return null;

		List<Stat> lista = dao.getKupljene();
		int brProdatih = lista.size();
		double prosecnaCena = 0;
		String najcescaRez = "";
		int rez1 = 0;
		int rez2 = 0;
		int rez3 = 0;
		for (Stat s : lista) {
			if (s.rezolucija.equals("1900x1080")) {
				rez1++;
			}
			if (s.rezolucija.equals("1600x900")) {
				rez2++;
			}
			if (s.rezolucija.equals("1280x720")) {
				rez3++;
			}
			prosecnaCena += s.cena;
		}

		prosecnaCena /= brProdatih;
		if (rez1 > rez2 && rez1 > rez3) {
			najcescaRez = "1900x1080";
		}
		if (rez2 > rez1 && rez2 > rez3) {
			najcescaRez = "1600x900";
		}
		if (rez3 > rez2 && rez3 > rez1) {
			najcescaRez = "1280x720";
		}

		return new StatistikaResponse(brProdatih, najcescaRez, prosecnaCena);
	}

}
