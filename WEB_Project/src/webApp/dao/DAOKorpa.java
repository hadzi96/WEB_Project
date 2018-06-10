package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webApp.entities.Item;
import webApp.entities.Photo;
import webApp.entities.User;
import webApp.reqests.AddItemReq;

public class DAOKorpa extends DAO<Item> {

	public DAOKorpa() {
		super(Item.class);
	}

	public boolean addItem(String user, Photo photo, String rezolucija, double cena) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format("SET @username := NULL;"
					+ "SELECT @username := `username` FROM user WHERE username = '%s';"
					+ "INSERT INTO `korpa` (`user`, `idSlike`, `popust`, `rezolucija`, `cena`, `optimisticLock`)"
					+ " VALUES (@username, %d, %b, '%s', %13.2f, 0)", user, photo.id, false, rezolucija, cena);
			PreparedStatement st = conn.prepareStatement(statement);

			st.execute();
			closeStat(st);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public List<Item> getKorpa(String username) {
		Connection conn = createConnection();

		List<Item> lista = new ArrayList<>();
		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM korpa WHERE user = '%s'", username);
			PreparedStatement st = conn.prepareStatement(statement);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				lista.add(readFromResultSet(rs));
			}

			closeStat(st);
			closeResultSet(rs);

			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return null;
	}

	public boolean buy(String user, int optimisticLock) {
		DAOPhoto daoPhoto = new DAOPhoto();
		List<Item> items = getKorpa(user);
		List<String> autors = new ArrayList<>();

		for (Item i : items) {
			autors.add(daoPhoto.getPhoto(i.idSlike, i.rezolucija).autor);
		}
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			for (Item i : items) {
				String statement = String.format("SET @username := NULL;"
						+ "SELECT @username := `username` FROM user WHERE username = '%s' AND optimisticLock = %d;"
						+ "INSERT INTO `kupljene` (`idSlike`, `buyer`, `autor`, `rezolucija`, `cena`)"
						+ " VALUES (%d, @username, '%s', '%s', %13.2f)", user, optimisticLock, i.idSlike,
						autors.get(items.indexOf(i)), i.rezolucija, i.cena);
				PreparedStatement st = conn.prepareStatement(statement);

				st.execute();
				closeStat(st);
				// --------------------------------------------------------------------------------------------------

				statement = String.format("DELETE FROM korpa WHERE id = %d;", i.id);
				st = conn.prepareStatement(statement);

				st.execute();
				closeStat(st);
			}

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

}
