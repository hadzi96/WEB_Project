package webApp.reqests;

import java.io.Serializable;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class OpenItemReq extends BasicEntity implements Serializable {

	public OpenItemReq() {
		super();
		this.columnsName.add(ID);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (ID.equals(columnName)) {
			this.id = UtilsMethods.saftyConversionInt(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (ID.equals(columnName)) {
			return this.id;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public int id;
	// constants
	public static final String ID = "id";
}