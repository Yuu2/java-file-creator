package model.impl;

import model.Model;

import java.util.ArrayList;
import java.util.List;

public class Table extends Model {

	List<Column> columns;

	public Table(String model) {
		super(model);
		columns = new ArrayList<>();
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	@Override
	public String toString() {
		return "Table{" +
				"columns=" + columns +
				", name='" + name + '\'' +
				'}';
	}
}
		