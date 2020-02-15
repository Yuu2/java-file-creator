package config;

import config.Property;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Yuu2
 * updated 2020.02.15
 */
public class Env {

  public static final String RESOURCE_PATH = System.getProperty("user.dir") + "/" + "resources";

  public static final String PROPERTY_PATH = RESOURCE_PATH  + "/property";

  public static final String SQL_PATH = RESOURCE_PATH + "/sql";

  // DB 관련 프로퍼티
  public static final Property DB = new Property("db.properties");

  // 환경설정 프로퍼티
  public static final Property CNF = new Property("config.properties");

  // 스레드풀 사이즈
  public static final ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(CNF.get("pool.size")));

}
