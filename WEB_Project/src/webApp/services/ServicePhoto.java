package webApp.services;

import java.util.Arrays;
import java.util.List;

import com.sun.java_cup.internal.runtime.Symbol;

import webApp.dao.DAOPhoto;
import webApp.dao.DAOProveraUser;
import webApp.entities.Photo;
import webApp.entities.req.OpenItemReq;
import webApp.entities.req.SearchItemReq;
import webApp.utils.UtilsMethods;

public class ServicePhoto {
	DAOPhoto dao;
	DAOProveraUser daoProvera;
	String[] searchO = { "ime", "autor", "kategorija", "keyword" };
	String[] filterO = { "datumR", "datumS", "brProdajeR", "brProdajeS", "cenaR", "cenaS", "imeR", "imeS", "ocenaR",
			"ocenaS" };
	List<String> searchOptions = Arrays.asList(searchO);
	List<String> filterOptions = Arrays.asList(filterO);

	public ServicePhoto() {
		this.dao = new DAOPhoto();
		this.daoProvera = new DAOProveraUser();
	}

	public List<Photo> search(SearchItemReq parameters) {
		if (!daoProvera.hasCookie(parameters.cookie))
			return null;

		int offst = UtilsMethods.saftyConversionInt(parameters.offset);

		// search AND filter
		if (parameters.search != null && searchOptions.contains(parameters.search) && parameters.filter != null
				&& filterOptions.contains(parameters.filter)) {
			System.out.println("SEARCH AND FILTER");
			return dao.search(UtilsMethods.searchAndFilterStatement(parameters.search, parameters.searchValue,
					parameters.filter, offst));
		}

		// search
		if (parameters.search != null && searchOptions.contains(parameters.search)
				&& (parameters.filter == null || !filterOptions.contains(parameters.filter))) {
			System.out.println("SEARCH");
			return dao.search(UtilsMethods.searchStatement(parameters.search, parameters.searchValue, offst));
		}

		// filter
		if (parameters.filter != null && filterOptions.contains(parameters.filter)
				&& (parameters.search == null || !searchOptions.contains(parameters.search))) {
			System.out.println("FILTER");
			return dao.search(UtilsMethods.filterStatement(parameters.filter, offst));
		}

		// getAll
		System.out.println("GET ALL");
		return dao.search(UtilsMethods.getAllStatement(offst));
	}

	// open just one (by id)
	public List<Photo> open(OpenItemReq parameters) {
		if (!daoProvera.hasCookie(parameters.cookie))
			return null;

		return dao.search(UtilsMethods.openStatement(parameters.id));
	}
}
