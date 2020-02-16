package service;

import com.mysql.jdbc.DatabaseMetaData;
import config.Env;
import model.impl.Column;
import model.impl.Schema;
import model.impl.Table;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuu2
 * updated 2020.02.15
 */
public interface AbstractManager extends AutoCloseable, Serializable {

  String username = Env.DB.get("username");
  String password = Env.DB.get("password");
  String host     = Env.DB.get("host");
  String port     = Env.DB.get("port");

  /**
   * 테이블 컬럼명 취득
   */
  private static Table findColumns(Table table, String url) {

    String sql = "SHOW COLUMNS FROM " + table.getDefaultName();

    try(
        Connection conn = DriverManager.getConnection(url, username, password);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
    ) {
      while(rs.next()) {
        String model = rs.getString(1);

        if(model == null)
        continue;

        table.addColumn(new Column(model));
      }
    } catch(Exception e) {
      // todo: Logger
    }
    return table;
  }
  
  /**
   * 데이터베이스 테이블 취득
   */
  public default List<Schema> findTables(String[] schemaArr) {

    return Arrays.asList(schemaArr).parallelStream().map(

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
}
