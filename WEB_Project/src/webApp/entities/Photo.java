package webApp.entities;

import java.io.Serializable;
import java.sql.Date;

import webApp.utils.UtilsMethods;

public class Photo extends BasicEntity implements Serializable {

	public Photo() {
		super();
		this.columnsName.add(ID);
		this.columnsName.add(IME);
		this.columnsName.add(AUTOR);
		this.columnsName.add(KATEGORIJA);
		this.columnsName.add(KEYWORD);
		this.columnsName.add(DATUM);
		this.columnsName.add(BR_PRODAJE);
		this.columnsName.add(CENA);
		this.columnsName.add(OPIS);
		this.columnsName.add(OCENA);
		this.columnsName.add(ON_SALE);
		this.columnsName.add(OPTIMISTIC_LOCK);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (OPTIMISTIC_LOCK.equals(columnName)) {
			this.optimisticLock = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (ID.equals(columnName)) {
			this.id = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (IME.equals(columnName)) {
			this.ime = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (AUTOR.equals(columnName)) {
			this.autor = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (KATEGORIJA.equals(columnName)) {
			this.kategorija = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (KEYWORD.equals(columnName)) {
			this.keyword = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (DATUM.equals(columnName)) {
			this.datum = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (BR_PRODAJE.equals(columnName)) {
			this.brProdaje = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (CENA.equals(columnName)) {
			this.cena = UtilsMethods.saftyConversionDouble(value);
			return;
		}
		if (OPIS.equals(columnName)) {
			this.opis = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (OCENA.equals(columnName)) {
			this.ocena = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (ON_SALE.equals(columnName)) {
			this.onSale = UtilsMethods.saftyConversionToBoolean(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (OPTIMISTIC_LOCK.equals(columnName)) {
			return this.optimisticLock;
		}

		if (ID.equals(columnName)) {
			return this.id;
		}
		if (IME.equals(columnName)) {
			return this.ime;
		}
		if (AUTOR.equals(columnName)) {
			return this.autor;
		}
		if (KATEGORIJA.equals(columnName)) {
			return this.kategorija;
		}
		if (KEYWORD.equals(columnName)) {
			return this.keyword;
		}
		if (DATUM.equals(columnName)) {
			return this.datum;
		}
		if (BR_PRODAJE.equals(columnName)) {
			return this.brProdaje;
		}
		if (CENA.equals(columnName)) {
			return this.cena;
		}
		if (OPIS.equals(columnName)) {
			return this.opis;
		}
		if (OCENA.equals(columnName)) {
			return this.ocena;
		}
		if (ON_SALE.equals(columnName)) {
			return this.onSale;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public int id;
	public String ime;
	public String autor;
	public String kategorija;
	public String keyword;
	public String datum;
	public int brProdaje;
	public double cena;
	public String opis;
	public String ocena;
	public boolean onSale;
	public int optimisticLock;

	// constants
	public static final String ID = "id";
	public static final String IME = "ime";
	public static final String AUTOR = "autor";
	public static final String KATEGORIJA = "kategorija";
	public static final String KEYWORD = "keyword";
	public static final String DATUM = "datum";
	public static final String BR_PRODAJE = "brProdaje";
	public static final String CENA = "cena";
	public static final String OPIS = "opis";
	public static final String OCENA = "ocena";
	public static final String ON_SALE = "onSale";
	public static final String OPTIMISTIC_LOCK = "optimisticLock";
}