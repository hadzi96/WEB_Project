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
import webApp.entities.req.AddItemReq;

public class DAOKorpa extends DAO<Item> {

	public DAOKorpa() {
		super(Item.class);
	}

	public boolean addItem(String user, Photo photo, String rezolucija, int optimisticLock) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format(
					"SET @username := NULL;"
							+ "SELECT @username := `username` FROM user WHERE username = '%s' AND optimisticLock = %d;"
							+ "INSERT INTO `korpa` (`user`, `idSlike`, `popust`, `rezolucija`, `optimisticLock`)"
							+ " VALUES (@username, %d, %b, '%s', 0)",
					user, optimisticLock, photo.id, false, rezolucija);
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

}
