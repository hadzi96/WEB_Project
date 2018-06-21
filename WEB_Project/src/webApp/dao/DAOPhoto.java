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

	public ArrayList<Photo> search(String statement) {
		Connection conn = createConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			ArrayList<Photo> list = new ArrayList<Photo>();
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
				String[] rez = photo.rezolucije.split(";");
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

	public Photo getPhoto(int id) {
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
			}

			closeStat(st);
			closeResultSet(rs);

			return photo;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return null;
	}

	public boolean addPhoto(Photo photo) {
		Connection conn = createConnection();

		if (conn == null)
			return false;

		try {
			String statement = String.format(
					"INSERT INTO `photo` (`ime`, `autor`, `kategorija`, `keyword`, `datum`, `mesto`, `brProdaje`, `cene`, `opis`, `ocena`, `onSale`,`rezolucije`,`fileName`, `optimisticLock`) VALUES\r\n"
							+ "('%s', '%s', '%s', '%s', now(), '%s', 0, '%s', '%s', '1', false, '%s','%s',0);",
					photo.ime, photo.autor, photo.kategorija, photo.keyword, photo.mesto, photo.cene, photo.opis,
					photo.rezolucije, photo.fileName);
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

	public ArrayList<Photo> getNeodobrene() {
		Connection conn = createConnection();
		if (conn == null)
			return null;

		try {
			String statement = String.format("SELECT * FROM photo WHERE onSale = false;");
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			ArrayList<Photo> list = new ArrayList<Photo>();
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

	public boolean odobri(int id) {
		Connection conn = createConnection();
		if (conn == null)
			return false;

		try {
			String statement = String.format("UPDATE photo SET onSale = true WHERE id = %d", id);
			PreparedStatement st = conn.prepareStatement(statement);
			st.execute();

			closeStat(st);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public List<Photo> getPhotosFromToday(String autor) {
		Connection conn = createConnection();
		if (conn == null)
			return null;

		try {
			String statement = String.format(
					"SELECT * FROM photo WHERE autor = '%s' AND datum > DATE_SUB(NOW(), INTERVAL 1 DAY);", autor);
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			ArrayList<Photo> list = new ArrayList<Photo>();
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

	public List<Photo> getPhotosFromWeek(String autor) {
		Connection conn = createConnection();
		if (conn == null)
			return null;

		try {
			String statement = String.format(
					"SELECT * FROM photo WHERE autor = '%s' AND datum > DATE_SUB(NOW(), INTERVAL 1 WEEK);", autor);
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();
			ArrayList<Photo> list = new ArrayList<Photo>();
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

	public boolean containsKupljene(int idSlike, String buyer) {
		Connection conn = createConnection();
		if (conn == null)
			return false;

		try {
			String statement = String.format("SELECT * FROM kupljene WHERE idSlike=%d AND buyer='%s';", idSlike, buyer);
			PreparedStatement st = conn.prepareStatement(statement);
			ResultSet rs = st.executeQuery();

			boolean state = false;
			if (rs.next()) {
				state = true;
			}

			closeStat(st);
			closeResultSet(rs);
			return state;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

	public boolean oceni(int idSlike, double ocena) {
		Connection conn = createConnection();
		if (conn == null)
			return false;

		try {
			String statement = String.format("UPDATE photo SET ocena = (ocena+%4.2f)/2 where id = %d;", ocena, idSlike);
			PreparedStatement st = conn.prepareStatement(statement);
			st.execute();

			closeStat(st);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return false;
	}

}
