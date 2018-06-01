package webApp.entities;

import java.io.Serializable;
import java.sql.Date;

import webApp.utils.UtilsMethods;

public class Item extends BasicEntity implements Serializable {

	public Item() {
		super();
		this.columnsName.add(ID);
		this.columnsName.add(USER);
		this.columnsName.add(ID_SLIKE);
		this.columnsName.add(POPUST);
		this.columnsName.add(REZOLUCIJA);
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
		if (USER.equals(columnName)) {
			this.user = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (ID_SLIKE.equals(columnName)) {
			this.idSlike = UtilsMethods.saftyConversionInt(value);
			return;
		}
		if (POPUST.equals(columnName)) {
			this.popust = UtilsMethods.saftyConversionToBoolean(value);
			return;
		}
		if (REZOLUCIJA.equals(columnName)) {
			this.rezolucija = UtilsMethods.saftyConversionToStr(value);
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
		if (USER.equals(columnName)) {
			return this.user;
		}
		if (ID_SLIKE.equals(columnName)) {
			return this.idSlike;
		}
		if (POPUST.equals(columnName)) {
			return this.popust;
		}
		if (REZOLUCIJA.equals(columnName)) {
			return this.rezolucija;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public int id;
	public String user;
	public int idSlike;
	public boolean popust;
	public String rezolucija;
	public int optimisticLock;

	// constants
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String ID_SLIKE = "idSlike";
	public static final String POPUST = "popust";
	public static final String REZOLUCIJA = "rezolucija";
	public static final String OPTIMISTIC_LOCK = "optimisticLock";
}