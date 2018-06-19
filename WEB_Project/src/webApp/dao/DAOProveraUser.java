package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webApp.entities.User;
import webApp.utils.CookieMethods;

public class DAOProveraUser extends DAO<User> {

	public DAOProveraUser() {
		super(User.class);
	}

	public User getUser(String cookie) {
		Connection conn = createConnection();
		String username = CookieMethods.getUsrnameFromCookie(cookie);
		User user = null;

		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn
					.prepareStatement(String.format("SELECT * FROM user WHERE username = '%s' AND isActive = true", username));

			ResultSet rs = st.executeQuery();

			if (rs.next())
				user = readFromResultSet(rs);

			closeStat(st);
			closeResultSet(rs);

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public User getUser(String username, String password) {
		Connection conn = createConnection();
		User user = null;

		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn.prepareStatement(
					String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'", username, password));

			ResultSet rs = st.executeQuery();
			if (rs.next())
				user = readFromResultSet(rs);

			closeStat(st);
			closeResultSet(rs);

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public User getUserbyName(String username) {
		Connection conn = createConnection();
		User user = null;

		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn
					.prepareStatement(String.format("SELECT * FROM user WHERE username = '%s'", username));

			ResultSet rs = st.executeQuery();
			if (rs.next())
				user = readFromResultSet(rs);

			closeStat(st);
			closeResultSet(rs);

			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

}
