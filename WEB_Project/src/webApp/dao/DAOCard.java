package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public List<Card> getCards(String user) {
		Connection conn = createConnection();

		List<Card> lista = new ArrayList<>();
		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM creditcard WHERE user = '%s'", user);
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
