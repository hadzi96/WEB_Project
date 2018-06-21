package webApp.entities;

import java.io.Serializable;

import webApp.utils.UtilsMethods;

public class Comment extends BasicEntity implements Serializable {

	public Comment() {
		super();
		this.columnsName.add(SENDER);
		this.columnsName.add(MESSAGE);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (SENDER.equals(columnName)) {
			this.sender = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (MESSAGE.equals(columnName)) {
			this.message = UtilsMethods.saftyConversionToStr(value);
			return;
		}
	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (SENDER.equals(columnName)) {
			return this.sender;
		}
		if (MESSAGE.equals(columnName)) {
			return this.message;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String sender;
	public String message;

	// constants
	public static final String SENDER = "sender";
	public static final String MESSAGE = "message";
}