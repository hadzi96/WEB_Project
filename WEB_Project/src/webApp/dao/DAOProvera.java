package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webApp.entities.User;

public class DAOProvera extends DAO<User> {

	public DAOProvera() {
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

}
