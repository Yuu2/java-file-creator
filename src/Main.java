import config.Env;
import model.Schema;
import service.db.DBManager;

import java.util.Arrays;
import java.util.List;

/**
 * @author Yuu2
 * updated 2020.02.15
 */
public class Main {

	public static void main(String[] args) throws Exception {

    String[] dbList = new String[] {"todoapp_db"};

    final Env env = new Env();

		DBManager manager = new DBManager();
		List<Schema> schema = manager.findSchema(dbList);

    System.out.println(schema);
	}
}
