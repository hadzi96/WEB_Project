package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webApp.entities.User;

public class DAOUser extends DAO<User> {

	public DAOUser() {
		super(User.class);
	}

	public boolean register(User user, String activationToken) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn.prepareStatement(String.format(
					"INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", false, \"%s\", \"%s\", \"%s\")",
					this.clazz.getSimpleName(), User.USERNAME, User.PASSWORD, User.EMAIL, User.DRZAVA, User.IS_ACTIVE,
					User.TYPE, User.ACTIVATION_TOKEN, User.OPTIMISTIC_LOCK, user.username, user.password, user.email,
					user.drzava, "kupac", activationToken, 0));

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

	public boolean login(String username, String password) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn
					.prepareStatement(String.format("SELECT * FROM %s WHERE %s = \"%s\" AND %s = \"%s\"",
							this.clazz.getSimpleName(), User.USERNAME, username, User.PASSWORD, password));

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

	public boolean contais(String username) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn.prepareStatement(String.format("SELECT * FROM %s WHERE %s = \"%s\"",
					this.clazz.getSimpleName(), User.USERNAME, username));

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

	// aktivacija naloga
	public boolean validateUser(String token) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			boolean status = false;
			ResultSet rs;
			PreparedStatement st;
			while (true) {
				st = conn.prepareStatement(String.format("SELECT * FROM %s WHERE %s = \"%s\" and isActive = false",
						this.clazz.getSimpleName(), User.ACTIVATION_TOKEN, token));

				rs = st.executeQuery();

				status = false;
				User user = null;
				if (rs.next()) {
					status = true;
					user = readFromResultSet(rs);
				} else {
					break;
				}

				closeStat(st);
				closeResultSet(rs);

				if (status) {
					st = conn.prepareStatement(String.format(
							"UPDATE %s SET isActive = true, %s = \"%s\" WHERE %s = \"%s\" and %s = \"%s\"",
							this.clazz.getSimpleName(), User.OPTIMISTIC_LOCK, user.optimisticLock + 1, User.USERNAME,
							user.username, User.OPTIMISTIC_LOCK, user.optimisticLock));
 
					st.execute();
					break;
				}

			}
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

}
