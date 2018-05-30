package webApp.services;

import java.util.Arrays;
import java.util.List;

import webApp.dao.DAOPhoto;
import webApp.dao.DAOProvera;
import webApp.entities.Photo;
import webApp.entities.Search;
import webApp.utils.UtilsMethods;

public class ServicePhoto {
	DAOPhoto dao;
	DAOProvera daoProvera;
	String[] searchO = { "ime", "autor", "kategorija", "keyword" };
	String[] filterO = { "datumR", "datumS", "brProdajeR", "brProdajeS", "cenaR", "cenaS", "imeR", "imeS", "ocenaR",
			"ocenaS" };
	List<String> searchOptions = Arrays.asList(searchO);
	List<String> filterOptions = Arrays.asList(filterO);

	public ServicePhoto(DAOPhoto dao) {
		this.dao = dao;
		this.daoProvera = new DAOProvera();
	}

	public List<Photo> search(Search parameters) {
		if (!daoProvera.hasCookie(parameters.cookie))
			return null;

		int offst = UtilsMethods.saftyConversionInt(parameters.offset);

		// search AND filter
		if (parameters.search != null && searchOptions.contains(parameters.search) && parameters.filter != null
				&& filterOptions.contains(parameters.filter)) {
			return dao.search(UtilsMethods.searchAndFilterStatement(parameters.search, parameters.searchValue,
					parameters.filter, offst));
		}

		// search
		if (parameters.search != null && searchOptions.contains(parameters.search)
				&& (parameters.filter == null || !filterOptions.contains(parameters.filter))) {
			return dao.search(UtilsMethods.searchStatement(parameters.search, parameters.search, offst));
		}

		// filter
		if (parameters.filter != null && filterOptions.contains(parameters.filter)
				&& (parameters.search == null || !searchOptions.contains(parameters.search))) {
			return dao.search(UtilsMethods.filterStatement(parameters.filter, offst));
		}

		// getAll
		return dao.search(UtilsMethods.getAllStatement(offst));
	}
}
