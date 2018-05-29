package webApp.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicEntity implements Serializable {
	private static final long serialVersionUID = -2156973367342327643L;

	protected List<String> columnsName;

	public BasicEntity() {
		this.columnsName = new ArrayList<String>();
	}

	public List<String> columnsName() {
		return this.columnsName;
	}

	public void setValueForColumnName(String columnName, Object value) {
	}

	public Object getValueForColumnName(String columnName) {
		return null;
	}
}
