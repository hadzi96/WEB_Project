package webApp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webApp.entities.BasicEntity;

public abstract class DAO<T extends BasicEntity> {
	public DAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	protected T readFromResultSet(ResultSet rs) {
		if (rs == null) {
			return null;
		}

		try {
			T object = this.clazz.newInstance();
			object = clazz.newInstance();
			for (String columnName : object.columnsName()) {
				object.setValueForColumnName(columnName, rs.getObject(columnName));
			}

			return object;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected Connection createConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/raf_photoshop?autoReconnect=true&useSSL=false&allowMultiQueries=true",
					USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}

		return null;
	}

	protected void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
	}

	protected void closeStat(PreparedStatement stat) {
		if (stat == null) {
			return;
		}

		try {
			stat.close();
		} catch (SQLException e) {
			// TODO nekim log framework-om ovo bi trebalo da se upisuje u log
			e.printStackTrace();
		}
	}

	protected void closeResultSet(ResultSet rs) {
		if (rs == null) {
			return;
		}

		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected Class<T> clazz;

	// constants
	protected String USERNAME = "root";
	protected String PASSWORD = "toor";
}
