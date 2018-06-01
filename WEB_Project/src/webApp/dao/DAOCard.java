package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import webApp.entities.Card;
import webApp.entities.User;

public class DAOCard extends DAO<Card> {

	public DAOCard() {
		super(Card.class);
	}

	public boolean addCard(String user, long number) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format("INSERT INTO creditCard(`user`, `number`) VALUES ('%s', '%s');", user,
					((Long) number).toString());
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

}
