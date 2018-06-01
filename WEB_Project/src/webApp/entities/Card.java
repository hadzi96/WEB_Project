package webApp.entities;

import java.io.Serializable;
import java.sql.Date;

import webApp.utils.UtilsMethods;

public class Card extends BasicEntity implements Serializable {

	public Card() {
		super();
		this.columnsName.add(ID);
		this.columnsName.add(USER);
		this.columnsName.add(NUMBER);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (ID.equals(columnName)) {
			this.id = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (USER.equals(columnName)) {
			this.user = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (NUMBER.equals(columnName)) {
			this.number = UtilsMethods.saftyConversionLong(value);
			return;
		}
	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (ID.equals(columnName)) {
			return this.id;
		}
		if (USER.equals(columnName)) {
			return this.user;
		}
		if (NUMBER.equals(columnName)) {
			return this.number;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public int id;
	public String user;
	public long number;

	// constants
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String NUMBER = "number";
}