package webApp.entities.req;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class GetCardReq extends BasicEntity implements Serializable {

	public GetCardReq() {
		super();
		this.columnsName.add(COOKIE);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (COOKIE.equals(columnName)) {
			this.cookie = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (COOKIE.equals(columnName)) {
			return this.cookie;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String cookie;

	// constants
	public static final String COOKIE = "cookie";
}