package webApp.entities.req;

import java.io.Serializable;
import java.sql.Date;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class AddOpReq extends BasicEntity implements Serializable {

	public AddOpReq() {
		super();
		this.columnsName.add(USERNAME);
		this.columnsName.add(PASSWORD);
		this.columnsName.add(EMAIL);
		this.columnsName.add(DRZAVA);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (USERNAME.equals(columnName)) {
			this.username = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (PASSWORD.equals(columnName)) {
			this.password = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (EMAIL.equals(columnName)) {
			this.email = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (DRZAVA.equals(columnName)) {
			this.drzava = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (USERNAME.equals(columnName)) {
			return this.username;
		}
		if (PASSWORD.equals(columnName)) {
			return this.password;
		}
		if (EMAIL.equals(columnName)) {
			return this.email;
		}
		if (DRZAVA.equals(columnName)) {
			return this.drzava;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String username;
	public String password;
	public String email;
	public String drzava;

	// constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String DRZAVA = "drzava";
}