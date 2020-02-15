package model;

public class Table {

  public String name;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return "Table{" + "name='" + name + '\'' + '}';
  }
}
