package service.finder;

import model.impl.Schema;
import model.impl.Table;
import service.AbstractFinder;

import java.util.List;

public class SQLFinder implements AbstractFinder {

  @Override
  public Table findColumns(Table table) {
    return null;
  }

  @Override
  public List<Schema> findTables(String[] schemaArray) {
    return null;
  }

  @Override
  public void close() throws Exception {

  }
}
