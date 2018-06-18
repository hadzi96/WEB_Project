package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webApp.entities.Photo;
import webApp.entities.User;
import webApp.responses.LoginResponse;
import webApp.utils.CookieMethods;
import webApp.utils.UtilsMethods;

public class DAOUser extends DAO<User> {
	DAOProveraUser provera = new DAOProveraUser();

	public DAOUser() {
		super(User.class);
	}

	public boolean register(User user, String activationToken) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn.prepareStatement(String.format(
					"INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %b, \"%s\", \"%s\", \"%s\")",
					this.clazz.getSimpleName(), User.USERNAME, User.PASSWORD, User.EMAIL, User.DRZAVA, User.IS_ACTIVE,
					User.TYPE, User.ACTIVATION_TOKEN, User.OPTIMISTIC_LOCK, user.username, user.password, user.email,
					user.drzava, user.isActive, user.type, activationToken, 0));

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
							"UPDATE %s SET isActive = true, %s = \"%s\", activationToken = \"utilized\" WHERE %s = \"%s\" and %s = \"%s\"",
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

	public LoginResponse login(User user) {
		Connection conn = createConnection();

		if (conn == null)
			return new LoginResponse(false);

		try {
			PreparedStatement st = conn.prepareStatement(
					String.format("SELECT * FROM %s WHERE %s = \"%s\" AND %s = \"%s\" AND isActive = true",
							this.clazz.getSimpleName(), User.USERNAME, user.username, User.PASSWORD, user.password));

			ResultSet rs = st.executeQuery();

			boolean status = false;
			if (rs.next())
				status = true;

			closeStat(st);
			closeResultSet(rs);

			if (status) {
				String cookie = CookieMethods.createCookie(user.username);
				return new LoginResponse(true, cookie);
			} else {
				return new LoginResponse(false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return new LoginResponse(false);
	}

	public Integer getLock(String username) {
		Connection conn = createConnection();

		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn.prepareStatement(String.format("SELECT * FROM %s WHERE %s = \"%s\"",
					this.clazz.getSimpleName(), User.USERNAME, username));

			ResultSet rs = st.executeQuery();

			Integer lock = null;
			if (rs.next())
				lock = rs.getInt(8);

			closeStat(st);
			closeResultSet(rs);

			return lock;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		return null;
	}

	public boolean deleteUser(String username) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn
					.prepareStatement(String.format("DELETE FROM user WHERE username = '%s'", username));

			st.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public boolean changePW(String username, String newPassword) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			PreparedStatement st = conn.prepareStatement(String.format(
					"UPDATE user SET password = '%s', isActive = true WHERE username = '%s' AND isActive = false;",
					newPassword, username));

			st.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public boolean block(String username) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format(
					"UPDATE user SET isActive = false WHERE username = '%s' AND type != 'admin' AND type != 'operater';"
							+ "DELETE FROM cookie WHERE username = '%s';",
					username, username);
			PreparedStatement st = conn.prepareStatement(statement);

			st.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public List<User> getUsersByType(String type) {
		Connection conn = createConnection();

		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM user WHERE type='%s';", type);

			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			List<User> list = new ArrayList<User>();
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
}
