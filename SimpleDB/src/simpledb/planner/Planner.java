package simpledb.planner;

import simpledb.parse.CreateTableData;
import simpledb.parse.Parser;
import simpledb.tx.Transaction;

/**
 * <p>Title:simpledb.planner.MyProject</p>
 * <p>
 * <p>Description: </p>
 * <p>
 * <p>Copyright: Copyright (c) 2001-2013 Newland SoftWare Company</p>
 * <p>
 * <p>Company: Newland SoftWare Company</p>
 *
 * @author Steven
 * @version 1.0 CreateTime：2017/12/9 18:52
 */
public class Planner {
	private QueryPlanner qplanner;
	private UpdatePlanner uplaner;

	public Planner(QueryPlanner qplanner, UpdatePlanner uplaner) {
		this.qplanner = qplanner;
		this.uplaner = uplaner;
	}

	public int executeUpdate(String cmd, Transaction tx) {
		Parser parser = new Parser(cmd);
		Object obj = parser.updateCmd();

		if (obj instanceof CreateTableData)
			return uplaner.executeCreateTable((CreateTableData) obj, tx);
		else
			return 0;
	}
}
