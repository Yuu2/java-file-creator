package service.generator;

import config.Env;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import service.AbstractGenerator;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FreeMakerGenerator implements AbstractGenerator {

  Configuration configuration;
  Template template;

  Map<String, Object> model;

  public FreeMakerGenerator() {
    model = new LinkedHashMap<>();
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
  public void addModel(String name, Object object) {
    model.put(name, object);
  }

  @Override
  public String read(String template) {
    String text = null;

    try(
      FileReader fr = new FileReader(Env.TEMPLATE_PATH + "/" + template);
      BufferedReader br = new BufferedReader(fr);
     ) {

      while(br.readLine() != null) {
        text += br.readLine();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return text;
  }

  @Override
  public void write() {

    try(
      Writer writer = new StringWriter();
    ) {
      template.process(model, writer);

      writer.flush();

      String display = writer.toString();

    } catch(Exception e) {
      // todo: Logger
    }
  }

  @Override
  public void close() throws Exception {
    // todo: Logger
  }
}
