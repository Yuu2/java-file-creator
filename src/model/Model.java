package model;

public abstract class Model{

  protected String defaultName;

  protected String upperCamelName;

  protected String lowerCamelName;

  public Model(String model) {
    this.defaultName    = model;
	  this.lowerCamelName = model;
	  this.upperCamelName = model;
  }

  public String getDefaultName() {
    return defaultName;
  }

  public String getUpperCamelName() {
    return upperCamelName;
  }

  public String getLowerCamelName() {
    return lowerCamelName;
  }

  public void setDefaultName(String defaultName) {
    this.defaultName = defaultName;
  }

  public void setUpperCamelName(String upperCamelName) {
    this.upperCamelName = upperCamelName;
  }

  public void setLowerCamelName(String lowerCamelName) {
    this.lowerCamelName = lowerCamelName;
  }

}
