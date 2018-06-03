package webApp.entities.req;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.Cookie;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class GetPhotoReq extends BasicEntity implements Serializable {

	public GetPhotoReq() {
		super();
		this.columnsName.add(FILE_NAME);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (FILE_NAME.equals(columnName)) {
			this.fileName = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (FILE_NAME.equals(columnName)) {
			return this.fileName;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String fileName;

	// constants
	public static final String FILE_NAME = "fileName";
}