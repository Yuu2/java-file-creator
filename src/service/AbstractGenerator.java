package service;

import model.impl.Schema;
import java.io.Serializable;
import java.util.List;

/**
 * @author Yuu2
 * updated 2020.02.17
 */
public interface AbstractGenerator extends AutoCloseable, Serializable {

  /**
   * 템플릿 세팅
   * @access public
   * @param fileName 파일 이름
   */
  public abstract void setTemplate(String fileName);

  /**
   * 모델 세팅
   * @access public
   * @param name 모델명
   * @param object 객체명
   */
  public abstract void setModel(String name, Object object);

  /**
   * 템플릿 읽기
   * @access public
   * @param template 템플릿 파일 이름
   */
  public abstract String read(String template);

  /**
   * 템플릿 쓰기
   * @access public
   * @param filePath 파일 경로
   */
  public abstract void write(String filePath);

  /**
   * 템플릿 렌더링
   * @access public
   * @param schemaList 스키마 리스트
   */
  public default void render(List<Schema> schemaList) {};

}
