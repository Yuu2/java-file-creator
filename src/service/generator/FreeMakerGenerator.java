package service.generator;

import config.Env;
import freemarker.template.Configuration;
import freemarker.template.Template;
import model.impl.Schema;
import service.AbstractGenerator;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FreeMakerGenerator implements AbstractGenerator {

  private List<String> containedTblList;
  private List<String> exceptedTblList;

  private Configuration configuration;
  private Template template;

  private Map<String, Object> model;

  public FreeMakerGenerator() {
    this.model = new LinkedHashMap<>();
    this.containedTblList = new ArrayList<>();
    this.exceptedTblList  = new ArrayList<>();
  }

  public void setContainedTblList(List<String> containedTblList) {
    this.containedTblList = containedTblList;
  }

  public void setExceptedTblList(List<String> exceptedTblList) {
    this.exceptedTblList = exceptedTblList;
  }

  @Override
  public void setTemplate(String fileName) {
    try {
      configuration = new Configuration();
      configuration.setDirectoryForTemplateLoading(new File(Env.TEMPLATE_PATH + "/"));

      template = configuration.getTemplate(fileName);

    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setModel(String name, Object object) {
    model.put(name, object);
  }

  @Override
  public String read(String template) {
    String line = null;

    try(
      FileReader fr = new FileReader(Env.TEMPLATE_PATH + "/" + template);
      BufferedReader br = new BufferedReader(fr);
     ) {

      while(br.readLine() != null) {
        line = br.readLine();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return line;
  }

  @Override
  public void write(String fileName) {

     try(
       PrintWriter printWriter = new PrintWriter(Env.OUTPUT_PATH + File.separator + fileName, "UTF-8");
       BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
       Writer templateWriter = new StringWriter();
     ) {

      template.process(model, templateWriter);
      templateWriter.flush();

      bufferedWriter.write(templateWriter.toString());
      bufferedWriter.flush();

    } catch(Exception e) {
       // todo: Logger
       e.printStackTrace();
    }
  }

  @Override
  public void render(List<Schema> schema) {

    schema.stream().forEach(db -> {

      db.getTables()
        .stream()
        .filter( tbl -> containedTblList.contains(tbl.getName()))
    //  .filter( tbl -> !exceptedTblList.contains(tbl.getName()))
        .collect(Collectors.toList())
        .stream()
        .forEach(tbl -> {

          setModel("schema", db.getName());
          setModel("upper_class_name", tbl.getUpperCamel());
          setModel("lower_class_name", tbl.getLowerCamel());
          setModel("columns", tbl.getColumns());

          write(tbl.getUpperCamel() + ".java");
        });
    });
  }

  @Override
  public void close() throws Exception {
    // todo: Logger
  }
}
