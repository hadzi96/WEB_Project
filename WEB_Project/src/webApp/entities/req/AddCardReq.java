package webApp.entities.req;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class AddCardReq extends BasicEntity implements Serializable {

	public AddCardReq() {
		super();
		this.columnsName.add(USER_LOCK);
		this.columnsName.add(CREDIT_CARD);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

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
	public int optimisticLock;
	public long creditCard;

	// constants
	public static final String USER_LOCK = "optimisticLock";
	public static final String CREDIT_CARD = "creditCard";
}