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
		this.columnsName.add(USER_LOCK);
		this.columnsName.add(CREDIT_CARD);
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
		if (CREDIT_CARD.equals(columnName)) {
			this.creditCard = UtilsMethods.saftyConversionLong(value);
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
		if (CREDIT_CARD.equals(columnName)) {
			return this.creditCard;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String cookie;
	public int optimisticLock;
	public long creditCard;

	// constants
	public static final String COOKIE = "cookie";
	public static final String USER_LOCK = "optimisticLock";
	public static final String CREDIT_CARD = "creditCard";
}