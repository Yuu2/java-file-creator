package service.finder;

import com.mysql.jdbc.DatabaseMetaData;
import config.Env;
import model.impl.Column;
import model.impl.Schema;
import model.impl.Table;
import service.AbstractFinder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBFinder implements AbstractFinder {

  String username = Env.DB.get("username");
  String password = Env.DB.get("password");
  String host     = Env.DB.get("host");
  String port     = Env.DB.get("port");

  @Override
  public Table findColumns(Table table, String url) {

    String sql = "SHOW COLUMNS FROM " + table.getName();

    try(
      Connection conn = DriverManager.getConnection(url, username, password);
      PreparedStatement preparedStatement = conn.prepareStatement(sql);
      ResultSet rs = preparedStatement.executeQuery();
    ) {

      while(rs.next()) {

        String field = rs.getString(1); // Field

        if(field == null)
        continue;

        table.addColumn(new Column(field));
      }
    } catch(Exception e) {
      // todo: Logger
    }
    return table;
  }

  @Override
  public List<Schema> findTables(String[] schemaArray) {

    return Arrays.asList(schemaArray).stream().map(

      db -> {
        Schema schema = new Schema(db);
        String url = host + ":" + port + "/" + db;

        try(
         Connection conn = DriverManager.getConnection(url, username, password);
        ) {

          // DB 메타데이터 취득
          DatabaseMetaData metaData = (DatabaseMetaData) conn.getMetaData();
          ResultSet rs = metaData.getTables(null, null, "%", new String[] {"TABLE"});

          // 테이블 정보 보존
          while(rs.next()) {
            String model = rs.getString("TABLE_NAME");
            Table table = new Table(model);
                  table = findColumns(table, url);
            schema.addTable(table);
          }
        } catch(Exception e) {
          // todo: Logger
        }
        return schema;
      }
    ).collect(Collectors.toList());
  }

  @Override
  public void close() throws Exception {}
}
