package webApp.entities;

import java.io.Serializable;
import java.sql.Date;

import webApp.utils.UtilsMethods;

public class DaoTest extends BasicEntity implements Serializable {

	public DaoTest() {
		super();
		this.columnsName.add(USER);
		this.columnsName.add(OCENA);
		this.columnsName.add(SLIKA1);
		this.columnsName.add(SLIKA2);
		this.columnsName.add(SLIKA3);
		this.columnsName.add(SLIKA4);
		this.columnsName.add(SLIKA5);
		this.columnsName.add(SLIKA6);
		this.columnsName.add(SLIKA7);
		this.columnsName.add(SLIKA8);
		this.columnsName.add(SLIKA9);
		this.columnsName.add(SLIKA10);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (USER.equals(columnName)) {
			this.user = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (OCENA.equals(columnName)) {
			this.ocena = UtilsMethods.saftyConversionDouble(value);
			return;
		}
		if (SLIKA1.equals(columnName)) {
			this.slika1 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA2.equals(columnName)) {
			this.slika2 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA3.equals(columnName)) {
			this.slika3 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA4.equals(columnName)) {
			this.slika4 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA5.equals(columnName)) {
			this.slika5 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA6.equals(columnName)) {
			this.slika6 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA7.equals(columnName)) {
			this.slika7 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA8.equals(columnName)) {
			this.slika8 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA9.equals(columnName)) {
			this.slika9 = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SLIKA10.equals(columnName)) {
			this.slika10 = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (USER.equals(columnName)) {
			return this.user;
		}
		if (OCENA.equals(columnName)) {
			return this.ocena;
		}
		if (SLIKA1.equals(columnName)) {
			return this.slika1;
		}
		if (SLIKA2.equals(columnName)) {
			return this.slika2;
		}
		if (SLIKA3.equals(columnName)) {
			return this.slika3;
		}
		if (SLIKA4.equals(columnName)) {
			return this.slika4;
		}
		if (SLIKA5.equals(columnName)) {
			return this.slika5;
		}
		if (SLIKA6.equals(columnName)) {
			return this.slika6;
		}
		if (SLIKA7.equals(columnName)) {
			return this.slika7;
		}
		if (SLIKA8.equals(columnName)) {
			return this.slika8;
		}
		if (SLIKA9.equals(columnName)) {
			return this.slika9;
		}
		if (SLIKA10.equals(columnName)) {
			return this.slika10;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String user;
	public double ocena;
	public String slika1;
	public String slika2;
	public String slika3;
	public String slika4;
	public String slika5;
	public String slika6;
	public String slika7;
	public String slika8;
	public String slika9;
	public String slika10;

	// constants
	public static final String USER = "user";
	public static final String OCENA = "ocena";
	public static final String SLIKA1 = "slika1";
	public static final String SLIKA2 = "slika2";
	public static final String SLIKA3 = "slika3";
	public static final String SLIKA4 = "slika4";
	public static final String SLIKA5 = "slika5";
	public static final String SLIKA6 = "slika6";
	public static final String SLIKA7 = "slika7";
	public static final String SLIKA8 = "slika8";
	public static final String SLIKA9 = "slika9";
	public static final String SLIKA10 = "slika10";

}