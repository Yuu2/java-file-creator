import config.Env;
import model.impl.Schema;
import service.AbstractGenerator;
import service.AbstractFinder;
import service.generator.FreeMakerGenerator;
import service.finder.DBFinder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuu2
 * updated 2020.02.17
 * todo: SQL Generator
 */
public class Main {

	public static void main(String[] args) {

		AbstractFinder finder = new DBFinder();
		AbstractGenerator generator = new FreeMakerGenerator();
		generator.setTemplate("mapperxml.ftl");

		/* DB 메타데이터 */
		List<Schema> schemaList = finder.findTables(new String[] {

		});

		/* 포함시킬 테이블 */
		List<String> containedTblList = Arrays.asList(new String[] {

		}).stream()
			.map(String::toLowerCase)
			.collect(Collectors.toList());;

		/* Import */
		List<String> imports = Arrays.asList(new String[] {

		});

		/* Annotation */
		List<String> annotations = Arrays.asList(new String[] {

		});

		/* Rendering */
		FreeMakerGenerator freeMakerGenerator = (FreeMakerGenerator) generator;
										   freeMakerGenerator.setContainTables(containedTblList);
										   freeMakerGenerator.setModel("imports", imports);
										   freeMakerGenerator.setModel("annotations", annotations);;
		                   freeMakerGenerator.render(schemaList);

		Env.THREAD_POOL.shutdown();
	}
}
