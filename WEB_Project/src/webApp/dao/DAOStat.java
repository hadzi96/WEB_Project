package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webApp.entities.BasicEntity;
import webApp.entities.Card;
import webApp.entities.Stat;
import webApp.entities.User;

public class DAOStat extends DAO<BasicEntity> {

	public DAOStat() {
		super(BasicEntity.class);
	}

	public List<Stat> getKupljene() {
		Connection conn = createConnection();

		List<Stat> lista = new ArrayList<>();
		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM kupljene");
			PreparedStatement st = conn.prepareStatement(statement);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Stat stat = new Stat();
				stat.cena = rs.getDouble("cena");
				stat.rezolucija = rs.getString("rezolucija");
				lista.add(stat);
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
