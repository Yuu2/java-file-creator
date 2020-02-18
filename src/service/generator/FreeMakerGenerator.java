package service.generator;

import config.Env;
import freemarker.template.Configuration;
import freemarker.template.Template;
import model.impl.Schema;
import service.AbstractGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FreeMakerGenerator implements AbstractGenerator {

  private List<String> containTables;
  private List<String> exceptTables;

  private Configuration configuration;
  private Template template;

  private Map<String, Object> model;

  public FreeMakerGenerator() {
    this.model = new LinkedHashMap<>();
    this.containTables = new ArrayList<>();
    this.exceptTables  = new ArrayList<>();
  }

  public void setContainTables(List<String> containedTblList) {
    this.containTables = containedTblList;
  }

  public void setExceptTables(List<String> exceptedTblList) {
    this.exceptTables = exceptedTblList;
  }

  @Override
  public void setTemplate(String fileName) {
    try {
      configuration = new Configuration();
      configuration.setDirectoryForTemplateLoading(new File(Env.TEMPLATE_PATH + "/"));

      template = configuration.getTemplate(fileName);

    } catch(Exception e) {
      e.printStackTrace(); // todo: Logger
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
      e.printStackTrace(); // todo: Logger
    }

    return line;
  }
  public void beforeWrite(String filePath) {

    try {
      Path path = Paths.get(filePath);

      if(!Files.exists(path)) {
        Files.createDirectories(path.getParent());
      }
    } catch(Exception e) {
      // todo: Logger
    }
  }

  @Override
  public void write(String filePath) {

    beforeWrite(filePath);

    Thread thread = new Thread(() -> {

      try(
        PrintWriter printWriter = new PrintWriter(filePath, "UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(printWriter);
        Writer templateWriter = new StringWriter();
      ) {

        template.process(model, templateWriter);
        templateWriter.flush();

        bufferedWriter.write(templateWriter.toString());
        bufferedWriter.flush();

      } catch(Exception e) {
        e.printStackTrace(); // todo: Logger
      }
    });
    Env.THREAD_POOL.submit(thread);
  }

  @Override
  public void render(List<Schema> schema) {

    schema.stream().forEach(db -> {

      db.getTables()
        .stream()
        .filter( tbl -> containTables.contains(tbl.getName()))
    //  .filter( tbl -> exceptTables.contains(tbl.getName()))
        .collect(Collectors.toList())
        .stream()
        .forEach(tbl -> {

          setModel("schema", db.getName());
          setModel("defaultClassName", tbl.getName());
          setModel("upperClassName", tbl.getUpperCamel());
          setModel("lowerClassName", tbl.getLowerCamel());
          setModel("columns", tbl.getColumns());

          write(Env.OUTPUT_PATH + "/" + db.getName() + "/" + tbl.getUpperCamel() + "Mapper.xml");
        });
    });
  }

  @Override
  public void close() throws Exception {
    // todo: Logger
  }
}
