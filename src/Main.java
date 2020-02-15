import com.google.common.base.CaseFormat;
import config.Env;
import model.Schema;
import model.Table;
import service.AbstractGenerator;
import service.AbstractManager;
import service.generator.FreeMakerGenerator;
import service.manager.Manager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuu2
 * updated 2020.02.15
 */
public class Main {

	public static void main(String[] args) {

		AbstractManager manager = new Manager();

		List<Schema> schema  = manager.findSchema(new String[] {"todoapp_db"});
		List<String> imports = Arrays.asList(new String[] {"java.io", "java.sql"});

		AbstractGenerator creator = new FreeMakerGenerator();
											creator.setTemplate("service.ftl");

		schema.parallelStream().forEach(db -> {
                        List<Table> tables = db.getTables().stream().map(t -> {
                          t.setName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CAMEL_CASE"));
                          return t;
                        }).collect(Collectors.toList());

                        creator.addModel("package", tables);
												creator.write();
		});

		Env.THREADPOOL.shutdown();
	}
}
