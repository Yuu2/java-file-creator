package service;

import model.impl.Schema;
import model.impl.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Yuu2
 * updated 2020.02.17
 */
public interface AbstractFinder extends AutoCloseable, Serializable {

  /**
   * 테이블 컬럼 취득
   * @access public
   * @param table 테이블 객체
   */
  public default Table findColumns(Table table) {
    return null;
  };

  /**
   * 테이블 컬럼 취득 (DB Finder)
   * @access public
   * @param table 테이블 객체
   * @param url   DB URL
   */
  public default Table findColumns(Table table, String url){
    return null;
  };

  /**
   * 데이터베이스 테이블 취득
   * @param schemaArray 접속대상 DB 명칭
   */
  public abstract List<Schema> findTables(String[] schemaArray);
}
