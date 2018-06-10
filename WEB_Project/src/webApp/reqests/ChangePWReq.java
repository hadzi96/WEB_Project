package webApp.reqests;

import java.io.Serializable;
import java.sql.Date;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class ChangePWReq extends BasicEntity implements Serializable {

	public ChangePWReq() {
		super();
		this.columnsName.add(USERNAME);
		this.columnsName.add(PASSWORD);
		this.columnsName.add(NEW_PASSWORD);
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
		if (NEW_PASSWORD.equals(columnName)) {
			this.newpassword = UtilsMethods.saftyConversionToStr(value);
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
		if (NEW_PASSWORD.equals(columnName)) {
			return this.newpassword;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String username;
	public String password;
	public String newpassword;

	// constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NEW_PASSWORD = "newpassword";
}