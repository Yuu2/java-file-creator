package service.db;

import com.mysql.jdbc.DatabaseMetaData;
import config.Env;
import model.Schema;
import model.Table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractDBManager implements Serializable, AutoCloseable {

  Connection conn;
  Statement stmt;

  String driver;
  String username;
  String password;
  String host;
  String port;

  public AbstractDBManager() {
    this.host     = Env.DB.get("host");
    this.port     = Env.DB.get("port");
    this.driver   = Env.DB.get("driver");
    this.username = Env.DB.get("username");
    this.password = Env.DB.get("password");
  }

  /**
   * 데이터베이스 정보 취득
   */
  public List<Schema> findSchema(String[] schema_arr) {

    return Arrays.asList(schema_arr).parallelStream().map(

        db -> {
          System.out.println(db);
          Schema schema = new Schema();
                 schema.setName(db);

          try(this) {

            // DB 연결
            Class.forName(driver);
            conn = DriverManager.getConnection(host + ":" + port + "/" + db, username, password);

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

  @Override
  public void close() throws Exception {
    // todo:
    System.out.println("커넥션 닫기");
    conn.close();
  }
}
