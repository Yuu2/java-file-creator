package model;

import com.google.common.base.CaseFormat;

public abstract class Model{

  protected String name;

  protected String upperCamel;

  protected String lowerCamel;

  protected String lowerCamelUnderScore;

  protected String upperCamelUnderScore;

  public Model(String model) {
    this.name = model.toLowerCase();
	  this.lowerCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, model);
	  this.upperCamel = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, model);
    this.lowerCamelUnderScore = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, model);
    this.upperCamelUnderScore = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, model);
  }

  public String getName() {
    return name;
  }

  public String getUpperCamel() {
    return upperCamel;
  }

  public String getLowerCamel() {
    return lowerCamel;
  }

  public String getLowerCamelUnderScore() {
    return lowerCamelUnderScore;
  }

  public String getUpperCamelUnderScore() {
    return upperCamelUnderScore;
  }
}
