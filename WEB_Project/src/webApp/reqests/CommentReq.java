package webApp.reqests;

import java.io.Serializable;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class CommentReq extends BasicEntity implements Serializable {

	public CommentReq() {
		super();
		this.columnsName.add(RECEIVER);
		this.columnsName.add(MESSAGE);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (RECEIVER.equals(columnName)) {
			this.receiver = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (MESSAGE.equals(columnName)) {
			this.message = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (RECEIVER.equals(columnName)) {
			return this.receiver;
		}
		if (MESSAGE.equals(columnName)) {
			return this.message;
		}
		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String receiver;
	public String message;

	// constants
	public static final String RECEIVER = "receiver";
	public static final String MESSAGE = "message";
}