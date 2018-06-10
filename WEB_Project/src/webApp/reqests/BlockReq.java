package webApp.reqests;

import java.io.Serializable;
import java.sql.Date;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class BlockReq extends BasicEntity implements Serializable {

	public BlockReq() {
		super();
		this.columnsName.add(USERNAME);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (USERNAME.equals(columnName)) {
			this.username = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (USERNAME.equals(columnName)) {
			return this.username;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String username;

	// constants
	public static final String USERNAME = "username";
}