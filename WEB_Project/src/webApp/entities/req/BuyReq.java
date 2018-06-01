package webApp.entities.req;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class BuyReq extends BasicEntity implements Serializable {

	public BuyReq() {
		super();
		this.columnsName.add(COOKIE);
		this.columnsName.add(USER_LOCK);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (COOKIE.equals(columnName)) {
			this.cookie = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (USER_LOCK.equals(columnName)) {
			this.optimisticLock = UtilsMethods.saftyConversionInt(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (COOKIE.equals(columnName)) {
			return this.cookie;
		}
		if (USER_LOCK.equals(columnName)) {
			return this.optimisticLock;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String cookie;
	public int optimisticLock;

	// constants
	public static final String COOKIE = "cookie";
	public static final String USER_LOCK = "optimisticLock";
}