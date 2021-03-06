package webApp.reqests;

import java.io.Serializable;

import webApp.entities.BasicEntity;
import webApp.utils.UtilsMethods;

public class SearchItemReq extends BasicEntity implements Serializable {

	public SearchItemReq() {
		super();
		this.columnsName.add(OFFSET);
		this.columnsName.add(SEARCH);
		this.columnsName.add(SEARCH_VALUE);
		this.columnsName.add(FILTER);
	}

	@Override
	public void setValueForColumnName(String columnName, Object value) {

		if (OFFSET.equals(columnName)) {
			this.offset = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SEARCH.equals(columnName)) {
			this.search = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (SEARCH_VALUE.equals(columnName)) {
			this.searchValue = UtilsMethods.saftyConversionToStr(value);
			return;
		}
		if (FILTER.equals(columnName)) {
			this.filter = UtilsMethods.saftyConversionToStr(value);
			return;
		}

	}

	@Override
	public Object getValueForColumnName(String columnName) {

		if (OFFSET.equals(columnName)) {
			return this.offset;
		}
		if (SEARCH.equals(columnName)) {
			return this.search;
		}
		if (SEARCH_VALUE.equals(columnName)) {
			return this.searchValue;
		}
		if (FILTER.equals(columnName)) {
			return this.filter;
		}

		return null;
	}

	// properties
	public static final long serialVersionUID = 1L;
	public String offset;
	public String search;
	public String searchValue;
	public String filter;
	// constants
	public static final String OFFSET = "offset";
	public static final String SEARCH = "search";
	public static final String SEARCH_VALUE = "searchValue";
	public static final String FILTER = "filter";
}