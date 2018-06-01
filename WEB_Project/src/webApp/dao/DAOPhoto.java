package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webApp.entities.Photo;

public class DAOPhoto extends DAO<Photo> {

	public DAOPhoto() {
		super(Photo.class);
	}

	public List<Photo> getAll(int offset) {

		return null;
	}

	public List<Photo> search(String statement) {
		Connection conn = createConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			List<Photo> list = new ArrayList<Photo>();
			while (rs.next()) {
				list.add(readFromResultSet(rs));
			}
			closeStat(st);
			closeResultSet(rs);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return null;
	}

	public List<Photo> filter(int offset, String filter) {

		return null;
	}

	public List<Photo> searchAndFilter(int offset, String search, String searchValue, String filter) {
		if (searchValue == null || searchValue.equals(""))
			return filter(offset, filter);

		return null;
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
