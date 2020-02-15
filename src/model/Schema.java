package model;

import java.util.ArrayList;
import java.util.List;

public class Schema {

  private String name;

  private List<Table> tables;

  public Schema() {
    this.tables = new ArrayList<>();
  }

  public void addTable(Table table) {
    this.tables.add(table);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Table> getTables() {
    return this.tables;
  }

  @Override
  public String toString() {
    return "Schema{" + "name='" + name + '\'' + ", tables=" + tables + '}';
  }
}
