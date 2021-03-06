package webApp.reqests;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class AddItemReq extends BasicEntity implements Serializable {

	public AddItemReq() {
		super();
		this.columnsName.add(ID_SLIKE);
		this.columnsName.add(REZOLUCIJA);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

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
	public int idSlike;
	public double cena;
	public String rezolucija;

	// constants
	public static final String ID_SLIKE = "idSlike";
	public static final String REZOLUCIJA = "rezolucija";
}