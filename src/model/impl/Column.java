package model.impl;

import model.Model;

public class Column extends Model {

  public Column(String model) {
    super(model);
  }

  @Override
  public String toString() {
    return name;
  }
}
