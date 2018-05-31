package webApp.entities.req;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class AddItemReq extends BasicEntity implements Serializable {

	public AddItemReq() {
		super();
		this.columnsName.add(COOKIE); // cookie od usera kome dodajemo
		this.columnsName.add(ID_SLIKE);
		this.columnsName.add(REZOLUCIJA);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (COOKIE.equals(columnName)) {
			this.cookie = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (ID_SLIKE.equals(columnName)) {
			this.idSlike = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (REZOLUCIJA.equals(columnName)) {
			this.rezolucija = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (COOKIE.equals(columnName)) {
			return this.cookie;
		}

		if (ID_SLIKE.equals(columnName)) {
			return this.idSlike;
		}

		if (REZOLUCIJA.equals(columnName)) {
			return this.rezolucija;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String cookie;
	public int idSlike;
	public double cena;
	public String rezolucija;
	public int optimisticLock;

	// constants
	public static final String COOKIE = "cookie";
	public static final String ID_SLIKE = "idSlike";
	public static final String REZOLUCIJA = "rezolucija";
}