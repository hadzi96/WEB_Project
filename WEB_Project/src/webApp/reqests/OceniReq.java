package webApp.reqests;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class OceniReq extends BasicEntity implements Serializable {

	public OceniReq() {
		super();
		this.columnsName.add(OCENA);
		this.columnsName.add(USERNAME);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (OCENA.equals(columnName)) {
			this.ocena = UtilsMethods.saftyConversionDouble(value);
			return;
		}
		if (USERNAME.equals(columnName)) {
			this.username = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (USERNAME.equals(columnName)) {
			return this.username;
		}
		if (OCENA.equals(columnName)) {
			return this.ocena;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public double ocena;
	public String username;

	// constants
	public static final String OCENA = "ocena";
	public static final String USERNAME = "username";
}