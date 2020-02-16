package model.impl;

import java.util.ArrayList;
import java.util.List;

import model.Model;

public class Schema extends Model {

  private List<Table> tables;

  public Schema(String model) {
	super(model);
    this.tables = new ArrayList<>();
  }

  public void addTable(Table table) {
    this.tables.add(table);
  }


  public List<Table> getTables() {
    return this.tables;
  }

  @Override
  public String toString() {
    return "Schema " +
        "{" +
          "  tables="       + tables +
          ", defaultName='" + defaultName + '\'' +
          ", upperCamelName='" + upperCamelName + '\'' +
          ", lowerCamelName='" + lowerCamelName + '\'' +
        "}";
  }
}
