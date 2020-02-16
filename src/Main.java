import com.google.common.base.CaseFormat;
import config.Env;
import model.impl.Schema;
import model.impl.Table;
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

		AbstractManager   manager   = new Manager();
		AbstractGenerator generator = new FreeMakerGenerator();
											generator.setTemplate("service.ftl");

		// DB 정보 취득
		List<Schema> schema = manager.findTables(new String[] {
			"todoapp_db"
		});

		// imports 세팅
		List<String> imports = Arrays.asList(new String[] {
				"java.io",
				"java.sql"
		});


		generator.addModel("imports", imports);
		generator.addModel("schema" , schema);

		System.out.println(schema.toString());
		generator.read("service.ftl");
		Env.THREAD_POOL.shutdown();
	}
}
