package webApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webApp.entities.DaoTest;

public class DAOTest extends DAO<DaoTest> {

	public DAOTest() {
		super(DaoTest.class);
	}

	public DaoTest getTest(String username) {
		Connection conn = createConnection();
		DaoTest test = null;

		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM test WHERE user = '%s'", username);
			PreparedStatement st = conn.prepareStatement(statement);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				test = readFromResultSet(rs);
			}

			closeStat(st);
			closeResultSet(rs);

			return test;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return null;
	}

	public List<DaoTest> getNeocenjene() {
		Connection conn = createConnection();
		List<DaoTest> tests = new ArrayList<>();

		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM test WHERE ocena IS NULL");
			PreparedStatement st = conn.prepareStatement(statement);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				tests.add(readFromResultSet(rs));
			}

			closeStat(st);
			closeResultSet(rs);

			return tests;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return null;
	}

	public boolean addPhoto(String username, ArrayList<String> fileNames) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format(
					"INSERT INTO `test` (`user`, `slika1`, `slika2`, `slika3`, `slika4`, `slika5`, `slika6`, `slika7`, `slika8`, `slika9`, `slika10`) VALUES\r\n"
							+ "('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');",
					username, fileNames.get(0), fileNames.get(1), fileNames.get(2), fileNames.get(3), fileNames.get(4),
					fileNames.get(5), fileNames.get(6), fileNames.get(7), fileNames.get(8), fileNames.get(9));
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

	public boolean oceniTest(String username, double ocena) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format("UPDATE test SET ocena = %4.2f WHERE user = '%s';", ocena, username);
			if (ocena > (4f / 5f)) {
				statement += String.format("UPDATE user SET type = 'prodavac' WHERE username = '%s';", username);
			}
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
