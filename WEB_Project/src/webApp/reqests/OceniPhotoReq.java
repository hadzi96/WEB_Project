package webApp.reqests;

import java.io.Serializable;
import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class OceniPhotoReq extends BasicEntity implements Serializable {

	public OceniPhotoReq() {
		super();
		this.columnsName.add(OCENA);
		this.columnsName.add(ID_SLIKE);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (OCENA.equals(columnName)) {
			this.ocena = UtilsMethods.saftyConversionDouble(value);
			return;
		}
		if (ID_SLIKE.equals(columnName)) {
			this.idSlike = UtilsMethods.saftyConversionInt(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (OCENA.equals(columnName)) {
			return this.ocena;
		}

		if (ID_SLIKE.equals(columnName)) {
			return this.idSlike;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public double ocena;
	public int idSlike;

	// constants
	public static final String OCENA = "ocena";
	public static final String ID_SLIKE = "idSlike";

}