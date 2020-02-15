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

		AbstractManager   manager = new Manager();
		AbstractGenerator creator = new FreeMakerGenerator();

		// DB 정보 취득
		List<Schema> schema = manager.getSchema(new String[] {

		});

		List<String> imports = Arrays.asList(new String[] {
				"java.io",
				"java.sql"
		});

		// creator.setTemplate("service.ftl");

		schema.parallelStream().forEach(db -> {
			// creator.addModel("package", tables);
			// creator.write();
		});

		Env.THREADPOOL.shutdown();
	}
}
