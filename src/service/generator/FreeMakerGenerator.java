package service.generator;

import config.Env;
import freemarker.template.Configuration;
import freemarker.template.Template;
import service.AbstractGenerator;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
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
  public void write() {
    try(this) {
      Writer writer = new StringWriter();
      template.process(model, writer);

      writer.flush();

      String display = writer.toString();

      System.out.println(display);
    } catch(Exception e) {
      // :todo
    }
  }

  @Override
  public void close() throws Exception {
    // todo:
  }
}
