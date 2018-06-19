package webApp.entities;

import java.io.Serializable;

import webApp.utils.UtilsMethods;

public class Stat extends BasicEntity implements Serializable {

	public Stat() {
		super();
		this.columnsName.add(ID);
		this.columnsName.add(REZOLUCIJA);
		this.columnsName.add(CENA);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (ID.equals(columnName)) {
			this.id = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (REZOLUCIJA.equals(columnName)) {
			this.rezolucija = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (CENA.equals(columnName)) {
			this.cena = UtilsMethods.saftyConversionDouble(value);
			return;
		}
	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (ID.equals(columnName)) {
			return this.id;
		}
		if (REZOLUCIJA.equals(columnName)) {
			return this.rezolucija;
		}
		if (CENA.equals(columnName)) {
			return this.cena;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public int id;
	public String rezolucija;
	public double cena;

	// constants
	public static final String ID = "id";
	public static final String REZOLUCIJA = "rezolucija";
	public static final String CENA = "cena";
}