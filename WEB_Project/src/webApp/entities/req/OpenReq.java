package webApp.entities.req;

import java.io.Serializable;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class OpenReq extends BasicEntity implements Serializable {

	public OpenReq() {
		super();
		this.columnsName.add(COOKIE);
		this.columnsName.add(ID);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (COOKIE.equals(columnName)) {
			this.cookie = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (ID.equals(columnName)) {
			this.id = UtilsMethods.saftyConversionInt(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (COOKIE.equals(columnName)) {
			return this.cookie;
		}
		if (ID.equals(columnName)) {
			return this.id;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String cookie;
	public int id;
	// constants
	public static final String COOKIE = "cookie";
	public static final String ID = "id";
}