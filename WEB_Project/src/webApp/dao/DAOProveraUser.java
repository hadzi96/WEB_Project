package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webApp.entities.User;

public class DAOProveraUser extends DAO<User> {

	public DAOProveraUser() {
		super(User.class);
	}

	public boolean hasCookie(String cookie) {
		deleteOldCookies();
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn
					.prepareStatement(String.format("SELECT * FROM cookie WHERE value = '%s'", cookie));

			ResultSet rs = st.executeQuery();

			boolean status = false;
			if (rs.next())
				status = true;

			closeStat(st);
			closeResultSet(rs);

			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return false;
	}

	public User getUser(String cookie) {
		deleteOldCookies();
		Connection conn = createConnection();
		String username = null;
		User user = null;

		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn
					.prepareStatement(String.format("SELECT * FROM cookie WHERE value = '%s'", cookie));

			ResultSet rs = st.executeQuery();

			if (rs.next())
				username = rs.getString(1);

			closeStat(st);
			closeResultSet(rs);

			st = conn.prepareStatement(String.format("SELECT * FROM user WHERE username = '%s'", username));

			rs = st.executeQuery();

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

	public void deleteOldCookies() {
		Connection conn = createConnection();

		if (conn == null)
			return;

		try {
			PreparedStatement st = conn.prepareStatement("DELETE FROM cookie WHERE timeout < NOW()");
			st.execute();
			closeStat(st);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}

	public User getUser(String username, String password) {
		deleteOldCookies();
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

}
