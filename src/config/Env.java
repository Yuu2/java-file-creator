package config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Yuu2
 * updated 2020.02.15
 */
public class Env {

  // 리소스 경로
  public static final String RESOURCE_PATH = System.getProperty("user.dir") + "/" + "resources";
  // 템플릿 경로
  public static final String TEMPLATE_PATH = System.getProperty("user.dir") + "/" + "template";
  // 프로퍼티 경로
  public static final String PROPERTY_PATH = RESOURCE_PATH  + "/property";
  // SQL 경로
  public static final String SQL_PATH      = RESOURCE_PATH + "/sql";
  // DB 관련 프로퍼티
  public static final Property DB = new Property("db.properties");
  // 환경설정 프로퍼티
  public static final Property CONFIG = new Property("config.properties");
  // 스레드풀 사이즈
  public static final ExecutorService THREADPOOL = Executors.newFixedThreadPool(Integer.parseInt(CONFIG.get("pool.size")));
}
