package webApp.entities;

import java.io.Serializable;

import webApp.utils.UtilsMethods;

public class User extends BasicEntity implements Serializable {

	public User() {
		super();
		this.columnsName.add(USERNAME);
		this.columnsName.add(PASSWORD);
		this.columnsName.add(EMAIL);
		this.columnsName.add(DRZAVA);
		this.columnsName.add(IS_ACTIVE);
		this.columnsName.add(TYPE);
		this.columnsName.add(ACTIVATION_TOKEN);
		this.columnsName.add(OPTIMISTIC_LOCK);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (OPTIMISTIC_LOCK.equals(columnName)) {
			this.optimisticLock = UtilsMethods.saftyConversionInt(value);
			return;
		}
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
		if (IS_ACTIVE.equals(columnName)) {
			this.isActive = UtilsMethods.saftyConversionToBoolean(value);
			return;
		}
		if (TYPE.equals(columnName)) {
			this.type = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (ACTIVATION_TOKEN.equals(columnName)) {
			this.activationToken = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (OPTIMISTIC_LOCK.equals(columnName)) {
			return this.optimisticLock;
		}
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
		if (IS_ACTIVE.equals(columnName)) {
			return this.isActive;
		}
		if (TYPE.equals(columnName)) {
			return this.type;
		}
		if (ACTIVATION_TOKEN.equals(columnName)) {
			return this.activationToken;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String username;
	public String password;
	public String email;
	public String drzava;
	public boolean isActive;
	public String type;
	public String activationToken;
	public int optimisticLock;

	// constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String DRZAVA = "drzava";
	public static final String IS_ACTIVE = "isActive";
	public static final String TYPE = "type";
	public static final String ACTIVATION_TOKEN = "activationToken";
	public static final String OPTIMISTIC_LOCK = "optimisticLock";
}