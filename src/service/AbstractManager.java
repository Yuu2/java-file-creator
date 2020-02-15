package service;

import com.mysql.jdbc.DatabaseMetaData;
import config.Env;
import model.Schema;
import model.Table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuu2
 * updated 2020.02.15
 */
public interface AbstractManager extends AutoCloseable, Serializable {

  String driver   = Env.DB.get("driver");
  String username = Env.DB.get("username");
  String password = Env.DB.get("password");
  String host     = Env.DB.get("host");
  String port     = Env.DB.get("port");

  /**
   * 데이터베이스 전체 정보 취득
   */
  public default List<Schema> findSchema(String[] schemaArr) {

    return Arrays.asList(schemaArr).parallelStream().map(

        db -> {

          Schema schema = new Schema();
          schema.setName(db);

          try(this) {

            // DB 연결
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(host + ":" + port + "/" + db, username, password);

            // DB 메타데이터 취득
            DatabaseMetaData metaData = (DatabaseMetaData) conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "%", new String[] {"TABLE"});

            // 테이블 정보 보존
            while(rs.next()) {
              Table table = new Table();
              table.setName(rs.getString("TABLE_NAME"));

              schema.addTable(table);
            }

          } catch(Exception e) {
            // todo:
            e.printStackTrace();
          }
          return schema;
        }

    ).collect(Collectors.toList());
  }
}
