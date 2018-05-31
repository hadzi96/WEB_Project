package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webApp.entities.AddItemReq;
import webApp.entities.Photo;
import webApp.entities.User;

public class DAOKorpa extends DAO<Photo> {

	public DAOKorpa() {
		super(Photo.class);
	}

	public boolean addItem(String user, Photo photo, String rezolucija) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format(
					"INSERT INTO `korpa` (`user`, `idSlike`, `popust`, `rezolucija`, `optimisticLock`) VALUES ('%s', %d, %b, '%s', 0)",
					user, photo.id, false, rezolucija);
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

	public Photo getPhoto(int id, String rezolucija) {
		Connection conn = createConnection();
		Photo photo = null;

		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM photo WHERE id = %d", id);
			PreparedStatement st = conn.prepareStatement(statement);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				photo = readFromResultSet(rs);
				String[] rez = photo.rezolucije.split(",");
				for (int i = 0; i < rez.length; i++) {
					if (rez[i].equals(rezolucija))
						return photo;
				}
			}

			closeStat(st);
			closeResultSet(rs);

			return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return null;
	}

}
